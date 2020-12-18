package com.moudle.xiecheng.net

import com.moudle.xiecheng.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * author：zhangxiaowen1
 * Date:2019-11-07
 * Description:
 */


object RetrofitManager {

    private const val TIME_OUT = 30

    private val client: OkHttpClient
        get() {
            val builder = OkHttpClient.Builder()
            val logging = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                logging.level = HttpLoggingInterceptor.Level.BODY
            } else {
                logging.level = HttpLoggingInterceptor.Level.BASIC
            }

            builder.addInterceptor(logging)
                .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)

            return builder.build()
        }

    fun <S> getService(serviceClass: Class<S>, baseUrl: String): S {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())  jiekewoten封装库
            .baseUrl(baseUrl)
            .build().create(serviceClass)
    }

    val service by lazy { getService(ApiService::class.java, ApiService.BASE_URL) }
}