package com.sesameweather.android.logic.model

import com.sesameweather.android.R

/**
 * 将天气代码，转换成 Sky 对象
 */
class Sky(val info: String, val icon: Int, val bg: Int)

//1. mapof 和 mutableMapOf 的用法：
// mapof 函數创建的是一个不可变的集合，不可变的集合指的是只能读取，不能修改 增加，删除
// mutableMapOf 创建的是一个可变的集合
//2. to 不是关键字 , 而是 一个 infix 函数， 函数前面加上 infix


//定义 顶级函数
private val sky = mapOf(
    "CLEAR_DAY" to Sky("晴", R.drawable.ic_clear_day, R.drawable.bg_clear_day),
    "CLEAR_NIGHT" to Sky("晴", R.drawable.ic_clear_night, R.drawable.bg_clear_night),
    "PARTLY_CLOUDY_DAY" to Sky(
        "多云", R.drawable.ic_partly_cloud_day, R.drawable.bg_partly_cloudy_day
    ),
    "PARTLY_CLOUDY_NIGHT" to Sky(
        "多云", R.drawable.ic_partly_cloud_night, R.drawable.bg_partly_cloudy_night
    ),
    "CLOUDY" to Sky("阴", R.drawable.ic_cloudy, R.drawable.bg_cloudy),
    "WIND" to Sky("大风", R.drawable.ic_cloudy, R.drawable.bg_wind),
    "LIGHT_RAIN" to Sky("小雨", R.drawable.ic_light_rain, R.drawable.bg_rain),
    "MODERATE_RAIN" to Sky("中雨", R.drawable.ic_moderate_rain, R.drawable.bg_rain),
    "HEAVY_RAIN" to Sky("大雨", R.drawable.ic_heavy_rain, R.drawable.bg_rain),
    "STORM_RAIN" to Sky("暴雨", R.drawable.ic_storm_rain, R.drawable.bg_rain),
    "THUNDER_SHOWER" to Sky("雷阵雨", R.drawable.ic_thunder_shower, R.drawable.bg_rain),
    "SLEET" to Sky("雨夹雪", R.drawable.ic_sleet, R.drawable.bg_rain),
    "LIGHT_SNOW" to Sky("小雪", R.drawable.ic_light_snow, R.drawable.bg_snow),
    "MODERATE_SNOW" to Sky("中雪", R.drawable.ic_moderate_snow, R.drawable.bg_snow),
    "HEAVY_SNOW" to Sky("大雪", R.drawable.ic_heavy_snow, R.drawable.bg_snow),
    "STORM_SNOW" to Sky("暴雪", R.drawable.ic_heavy_snow, R.drawable.bg_snow),
    "HAIL" to Sky("冰雹", R.drawable.ic_hail, R.drawable.bg_snow),
    "LIGHT_HAZE" to Sky("轻度雾霾", R.drawable.ic_light_haze, R.drawable.bg_fog),
    "MODERATE_HAZE" to Sky("中度雾霾", R.drawable.ic_moderate_haze, R.drawable.bg_fog),
    "HEAVY_HAZE" to Sky("重度雾霾", R.drawable.ic_heavy_haze, R.drawable.bg_fog),
    "FOG" to Sky("雾", R.drawable.ic_fog, R.drawable.bg_fog),
    "DUST" to Sky("浮尘", R.drawable.ic_fog, R.drawable.bg_fog)
)
//顶级函数
fun addSky() {
    val skyNo = Sky("not_sky", 0, 0)
    //        sky["sss"] = skyNo // 报错 因为是通过 mapOf 函数创建的集合是不可变的
}
//顶级函数
fun getSky(skycon: String): Sky {
    //为空的话 返回 sky["CLEAR_DAY"]   !! 强制不判空
    return sky[skycon] ?: sky["CLEAR_DAY"]!!
}