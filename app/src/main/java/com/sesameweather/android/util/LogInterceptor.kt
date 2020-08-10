package com.sesameweather.android.util

import android.util.Log.i
import okhttp3.Interceptor
import okhttp3.Response
import java.text.SimpleDateFormat
import java.util.*


/**
 * @author zhouchao
 * @date 2020/7/21
 */
class LogInterceptor : Interceptor {

    val tag = " 自定义打印 LogInterceptor  Retrofit"
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")

    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()

        i(tag, format.format(Date()) + " Requeste " + "\nmethod:" + request.method() + "\nurl:" + request.url() + "\nbody:" + request.body())

        var response = chain.proceed(request)

        //response.peekBody不会关闭流
        i(tag, format.format(Date()) + " Response " + "\nsuccessful:" + response.isSuccessful + "\nbody:" + response.peekBody(1024)?.string())

        return response
    }

}