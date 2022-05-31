package cn.edu.swu.reptile_android.net

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 网络请求Api（单例）
 * */
object NetApi{
    private const val base_url = "http://119.3.77.254:8000"

    private var mRetrofit: Retrofit? = null
    private var mOkHttpClient: OkHttpClient? = null

    fun getRetrofit(): Retrofit{
        if(mRetrofit == null){
            mRetrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(base_url)
                .client(getOkHttpClient())
                .build()
        }
        return mRetrofit!!
    }

    private fun getOkHttpClient(): OkHttpClient {
        if(mOkHttpClient == null){
            mOkHttpClient = OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS) //设置读取超时时间
                .writeTimeout(10, TimeUnit.SECONDS) //设置写的超时时间
                .connectTimeout(10, TimeUnit.SECONDS).build()
        }
        return mOkHttpClient as OkHttpClient
    }
}