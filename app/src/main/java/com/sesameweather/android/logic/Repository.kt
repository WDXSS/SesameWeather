package com.sesameweather.android.logic

import androidx.lifecycle.liveData
import com.sesameweather.android.logic.model.Place
import com.sesameweather.android.logic.model.Weather
import com.sesameweather.android.logic.network.SunnyWeatherNetwork
import com.sesameweather.android.logic.network.SunnyWeatherNetwork2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.RuntimeException


/**
 *
 * 仓库层  统一入口
 *
 *  CoroutineScope() 函数  创建一个 CoroutineScope 对象，通过CoroutineScope launch{} 创建一个协程
 *  coroutineScope{}  继承外部作用域，创建一下子作用域
 *  suspendCoroutine{}  挂起当前协程， lambda 表达式 将会在一个 线程中执行，通过  continuation.resume() 返回到当前协程
 * @author zhouchao
 * @date 2020/7/28
 */
object Repository {

    //liveData() 函数的作用
    //1. liveData() 函数是 androidx.lifecycle:lifecycle-livedata-ktx 库中提供的一个函数，
    // 1.2 会自动构建以个 LiveData 对象，
    // 1.3 在代码块中 提供一个挂起函数的上下文，所以可以调用 挂起函数
    // 1.4 liveData() 函数中调用了 CoroutineScope() 函数，创建 CoroutineScope 对象，通过CoroutineScope launch{} 创建一个协程

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

    // 第二阶段请求天气
    //
    fun refreshWeather(lng: String, lat: String, placeName: String) = liveData(Dispatchers.IO) {
        // 1. coroutineScope 继承外部的 作用域，创建子协程
        val result = coroutineScope {
            //2. async{} 函数创建一个 子协程，并且返回 Deferred ，通 await() 可以获取到执行结果
            // 创建多个 子协程 并行执行 请求天气 接口
            val deferredRealtime = async {
                SunnyWeatherNetwork2.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                SunnyWeatherNetwork2.getDailyWeather(lng, lat)
            }
            // 2.2 await() 可以获取到执行结果, 如果 协程 未执行完成，await 函数会阻塞 协程
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()

            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            } else {
                Result.failure<Weather>(
                    RuntimeException(
                        "realtime response status is ${realtimeResponse.status}" + "daily response status is ${dailyResponse.status}"
                    )
                )
            }
        }
        emit(result)
    }
}