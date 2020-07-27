package com.sesameweather.android.logic.network

import com.sesameweather.android.SesameWeatherApplication
import com.sesameweather.android.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author zhouchao
 * @date 2020/7/27
 */
interface PlaceService {

    @GET("v2/place?token=${SesameWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
}