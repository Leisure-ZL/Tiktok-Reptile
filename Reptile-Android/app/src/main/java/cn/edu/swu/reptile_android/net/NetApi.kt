package cn.edu.swu.reptile_android.net

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetApi{

    private const val base_url = "http://192.168.0.1:8080"

    fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(base_url)
            .client(getOkHttpClient())
            .build()
    }

    fun getOkHttpClient(): OkHttpClient {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS) //设置读取超时时间
            .writeTimeout(10, TimeUnit.SECONDS) //设置写的超时时间
            .connectTimeout(10, TimeUnit.SECONDS)
        return builder.build()

    }

}