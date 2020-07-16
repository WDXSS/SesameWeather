package com.sesameweather.android

import android.app.Application
import android.content.Context

/**
 * @author zhouchao
 * @date 2020/7/16
 */
class SesameWeatherApplication : Application() {
    //使用伴生类，模拟 static 常量
    companion object{
       lateinit var  context: Context
        const val TOKEN = "T68gEhm80PlGFr1Y"
    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}