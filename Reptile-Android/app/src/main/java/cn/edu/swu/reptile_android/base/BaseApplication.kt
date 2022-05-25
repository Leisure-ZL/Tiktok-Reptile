package cn.edu.swu.reptile_android.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import cn.edu.swu.reptile_android.model.entity.Video

class BaseApplication : Application() {

    //TODO:记住密码功能
    //TODO:捕获500错误异常
    //TODO:沉浸式状态栏

    /**
     * 详情视频列表数据
     * */
    var detailList: List<Video>? = null

    /**
     * singleton context
     * */
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

    }



}