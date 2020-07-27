package com.sesameweather.android.logic.network

import com.sesameweather.android.util.LogInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *  定义 Retrofit 的 构造器
 * @author zhouchao
 * @date 2020/7/27
 */
object ServiceCreator {
    private const val BASE_URL = "https://api.caiyunapp.com/"
    private var client = OkHttpClient.Builder()
        //自定义拦截器用于日志输出
        .addInterceptor(LogInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client).build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
    inline fun <reified T> create(): T = create(T::class.java)
}