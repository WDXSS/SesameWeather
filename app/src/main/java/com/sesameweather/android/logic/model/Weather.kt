package com.sesameweather.android.logic.model

/**
 * 定义 天气 Weather 数据模型，包含当前天气和未来几天的天气
 */
class Weather(val realtime: RealtimeResponse.Realtime, val daily: DailyResponse.Daily)