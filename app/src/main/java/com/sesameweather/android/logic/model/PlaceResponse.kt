package com.sesameweather.android.logic.model

import com.google.gson.annotations.SerializedName

/**
 *
 * 定义 --- 数据模型
 * @author zhouchao
 * @date 2020/7/27
 */

data class PlaceResponse(val status: String, val places: List<Place>)

data class Place(
    val name: String,
    val location: Location,
    @SerializedName("formatted_address") val address: String
)

data class Location(val lng: String, val lat: String)
