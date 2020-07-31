package com.sesameweather.android.logic.network

import com.sesameweather.android.SesameWeatherApplication
import com.sesameweather.android.logic.model.DailyResponse
import com.sesameweather.android.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * 定义 请求 天气的 接口Api
 * @author zhouchao
 * @date 2020/7/31
 */
interface WeatherService {
    @GET("v2.5/${SesameWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(
        @Path("lng") lng: String, @Path("lat") lat: String
    ): Call<RealtimeResponse>


    @GET("v2.5/${SesameWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<DailyResponse>
}