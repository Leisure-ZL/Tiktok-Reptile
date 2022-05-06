package cn.edu.swu.reptile_android.ui.video

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import cn.edu.swu.reptile_android.R
import cn.edu.swu.reptile_android.model.entity.Video
import cn.edu.swu.reptile_android.ui.base.BaseActivity
import cn.edu.swu.reptile_android.ui.base.BaseAdapter
import cn.edu.swu.reptile_android.ui.base.BaseApplication
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer


class VideoDetailActivity : BaseActivity() {

    val that = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detail)

        val pos = intent.getIntExtra("position", 0)

        val app = this.application as BaseApplication

        Log.d("tag", app.detailList.toString())
        val data = app.detailList?.subList(pos, app.detailList!!.size)

        val rv: RecyclerView = findViewById(R.id.rv)

        val adapter = data?.let {
            BaseVideoAdapter<Video>(R.layout.item_rv_detail_video, it) { view, video ->
                val player = view.findViewById<StandardGSYVideoPlayer>(R.id.detail_player)
                val url = "https:${video.videoUrl}"
                player.setUpLazy(url, true, null, null, video.videoName)
                //增加title
                player.titleTextView.visibility = View.VISIBLE
                //设置返回键
                player.backButton.visibility = View.VISIBLE
                //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏
                player.isAutoFullWithSize = true
                //音频焦点冲突时是否释放
                player.isReleaseWhenLossAudio = false
                //小屏时不触摸滑动
                player.setIsTouchWiget(false)
                //设置循环
                player.isLooping = true
              //  player.startPlayLogic()

            }
        }
        rv.adapter = adapter
        rv.layoutManager = PagerLayoutManager(this)


    }



    override fun onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        GSYVideoManager.onPause()
    }

    override fun onResume() {
        super.onResume()
        GSYVideoManager.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
    }

}