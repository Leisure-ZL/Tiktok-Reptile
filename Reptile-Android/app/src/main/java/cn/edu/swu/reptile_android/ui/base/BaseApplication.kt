package cn.edu.swu.reptile_android.ui.base

import android.app.Application
import cn.edu.swu.reptile_android.model.entity.Video
import leakcanary.LeakCanary

class BaseApplication : Application() {

    /**
     * 详情视频列表数据
     * */
    var detailList: List<Video>? = null


    override fun onCreate() {
        super.onCreate()


    }


}