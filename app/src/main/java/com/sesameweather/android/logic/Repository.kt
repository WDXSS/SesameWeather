package com.sesameweather.android.logic

import androidx.lifecycle.liveData
import com.sesameweather.android.logic.model.Place
import com.sesameweather.android.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.RuntimeException


/**
 *
 * 仓库层  统一入口
 * @author zhouchao
 * @date 2020/7/28
 */
object Repository {

    //liveData() 函数的作用
    //1.
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure<List<Place>>(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }

    fun searchPlaces2(query: String) = liveData(Dispatchers.IO) {
        var result: Result<List<Place>>? = null
        val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            result = Result.success(placeResponse.places)
        } else {
            result = Result.failure<List<Place>>(RuntimeException())
        }
        emit(result)
    }
}