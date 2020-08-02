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
import kotlin.coroutines.CoroutineContext


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
        // 相当于 setValue 通知LiveData 数据更新
        emit(result)
    }


    //定义 高阶函数  将 try{} 统一处理
    // 1. 函数类型 参数的返回值 Result<T>
    // 2. liveData 函数 持有挂起函数的上下文，但是 函数类型 参数 block() 不会有挂起函数的上下文，所以添加了 关键字  suspend  即：block: suspend ()
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }


    private fun searchPlaces3(query: String) = fire(Dispatchers.IO) {
        // SunnyWeatherNetwork.searchPlaces(query) 函数是个挂起函数，需要在 协程的作用域或者 挂起函数中调用
        // 所以 fire函数中 函数类型参数添加了 关键字 suspend --- block: suspend ()
        val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            //最后一条语句 作为 lambda 表达式的 返回值
            Result.success(placeResponse.places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

}