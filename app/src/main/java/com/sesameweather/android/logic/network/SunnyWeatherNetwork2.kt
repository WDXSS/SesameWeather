package com.sesameweather.android.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * 请求 天气的 单例类 和 SunnyWeatherNetwork 一样只为多写一次
 * @author zhouchao
 * @date 2020/7/31
 */
object SunnyWeatherNetwork2 {
    private const val TAG = "SunnyWeatherNetwork2"

    private val weatherService = ServiceCreator.create(WeatherService::class.java)

    suspend fun getRealtimeWeather(lng: String, lat: String) =
        weatherService.getRealtimeWeather(lng, lat).await()

    suspend fun getDailyWeather(lng: String, lat: String) = weatherService.getDailyWeather(lng, lat).await()


    private suspend fun <T> Call<T>.await(): T {

        return suspendCoroutine<T> { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) {
                        continuation.resume(body)
                    } else {
                        continuation.resumeWithException(RuntimeException("response body is null"))
                    }
                }
            })
        }

    }
}