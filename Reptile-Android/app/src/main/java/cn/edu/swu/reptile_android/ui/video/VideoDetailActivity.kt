package cn.edu.swu.reptile_android.ui.video

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import cn.edu.swu.reptile_android.R
import com.shuyu.gsyvideoplayer.GSYBaseActivityDetail
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer

class VideoDetailActivity : GSYBaseActivityDetail<StandardGSYVideoPlayer>() {
    lateinit var detailPlayer: StandardGSYVideoPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detail)



        detailPlayer = findViewById(R.id.detail_player);
        //增加title
        detailPlayer.titleTextView.visibility = View.GONE;
        detailPlayer.backButton.visibility = View.GONE;

        initVideoBuilderMode();

    }

    override fun getGSYVideoPlayer(): StandardGSYVideoPlayer {
        return detailPlayer
    }

    override fun getGSYVideoOptionBuilder(): GSYVideoOptionBuilder {
       // val url = "https://www.douyin.com/aweme/v1/play/?video_id=v0200fg10000c9dp1cjc77uat3hamu20&line=0&file_id=a5a8d19da0d64c81aa17876b90bc5ca8&sign=502fd59cf6d6b65ecc2d247e9ac55f69&is_play_url=1&source=PackSourceEnum_AWEME_DETAIL&aid=6383"
        val url = intent.getStringExtra("url")
        return GSYVideoOptionBuilder()
            .setUrl(url)
            .setCacheWithPlay(true)
            .setVideoTitle(" ")
            .setIsTouchWiget(true)
            .setRotateViewAuto(false)
            .setLockLand(false)
            .setShowFullAnimation(false)
            .setNeedLockFull(true)
            .setSeekRatio(1f)
    }

    override fun clickForFullScreen() {
    }

    override fun getDetailOrientationRotateAuto(): Boolean {
        return false
    }
}