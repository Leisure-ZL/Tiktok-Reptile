package cn.edu.swu.reptile_android.ui.video

import android.view.View
import cn.edu.swu.reptile_android.R
import cn.edu.swu.reptile_android.ui.base.BaseAdapter
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer

class BaseVideoAdapter<T>(
    private val layoutResourceId: Int,
    private val videoData: List<T>,
    private val init: (View, T) -> Unit
) : BaseAdapter<T>(layoutResourceId, videoData, init){

    /**
     * 加载视频时自动播放视频
     * */
    override fun onViewAttachedToWindow(holder: ViewHolder<T>) {
        super.onViewAttachedToWindow(holder)

        holder.itemView.findViewById<StandardGSYVideoPlayer>(R.id.detail_player).startPlayLogic()

    }





}