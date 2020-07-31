package com.sesameweather.android.logic.model

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * 定义 未来几天的 天气信息
 *  将 未来几天天气 （DailyResponse.Daily） 和 当前天气（RealtimeResponse） 封装在 Weather 类中
 */
class DailyResponse(val status: String, val result: Result) {

    class Result(val daily: Daily)

    class Daily(val temperature: List<Temperature>, val skycon: List<Skycon>, @SerializedName("life_index") val lifeIndex: LifeIndex)

    class Temperature(val max: Float, val min: Float)

    class Skycon(val value: String, val date: Date)

    class LifeIndex(val coldRisk: List<LifeDescription>, val carWashing: List<LifeDescription>, val ultraviolet: List<LifeDescription>, val dressing: List<LifeDescription>)

    class LifeDescription(val desc: String)

}