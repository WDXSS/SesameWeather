package com.sesameweather.android.logic.network

import android.app.DownloadManager
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @author zhouchao
 * @date 2020/7/28
 */
object SunnyWeatherNetwork {
    private const val TAG = "SunnyWeatherNetwork"
    private val placeService = ServiceCreator.create(PlaceService::class.java)

    //private val service2 = ServiceCreator.create<PlaceService>()

    // Call 的 扩展函数 await()定义 为挂起函数
    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()

    //1. suspend 定义为挂起函数
    //2. suspendCoroutine {} 必须再挂起函数 或者协程作用域中调用
    // 2.1 函数接收一个 lambda 表达式，作用是：将当前协程直接直接挂起，在新的线程中执行 lambda 表达式
    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    Log.d(
                        TAG,
                        "onResponse() called with: call = $call, response = ${response.body()
                            .toString()}"
                    )
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }

            })


        }
    }
}