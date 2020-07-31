package com.sesameweather.android.logic.model

import com.google.gson.annotations.SerializedName

/**
 *第二阶段，查询天气
 * 注意和 第一阶段的 PlaceResponse.kt 的区别
 * data 和 非data 类
 *
 *  将 未来几天天气 （DailyResponse.） 和 当前天气（RealtimeResponse.Realtime） 封装在 Weather 类中
 */
class RealtimeResponse( val status: String,  val result: Result) {
    //将所有的 数据模型类都定义在 RealtimeResponse 防止 同名的冲突
    class Result( val realtime: Realtime)
    class Realtime(val skycon: String, val temperature: Float, @SerializedName("air_quality") val airQuality: AirQuality)
    class AirQuality(val aqi: AQI)
    class AQI(val chn: Float)
}