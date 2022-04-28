package cn.edu.swu.reptile_android.viewmodel

import androidx.lifecycle.MutableLiveData
import cn.edu.swu.reptile_android.base.BaseResponse
import cn.edu.swu.reptile_android.model.entity.User
import cn.edu.swu.reptile_android.model.entity.Video
import cn.edu.swu.reptile_android.repository.VideoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class VideoViewModel {

    private val videoRepo = VideoRepository()

    /**
     * dropdown menu data
     * */
    val dropdownData = listOf("昨日点赞增量排行", "昨日评论增量排行", "昨日收藏增量排行",
        "点赞总量排行", "评论总量排行", "收藏总量排行")

    /**
     * rv data
     * */
    val rvData: MutableLiveData<BaseResponse<List<Video>>> = MutableLiveData()

    fun getVideoByLikeInc() {
        GlobalScope.launch(Dispatchers.Main) {
            val resp = videoRepo.getVideoByLikeInc(100)
            rvData.value = resp
        }
    }

    fun getVideoByCommentInc() {
        GlobalScope.launch(Dispatchers.Main) {
            val resp = videoRepo.getVideoByCommentInc(100)
            rvData.value = resp
        }
    }

    fun getVideoByCollectInc() {
        GlobalScope.launch(Dispatchers.Main) {
            val resp = videoRepo.getVideoByCollectInc(100)
            rvData.value = resp
        }
    }

    fun getVideoByLike() {
        GlobalScope.launch(Dispatchers.Main) {
            val resp = videoRepo.getVideoByLike(100)
            rvData.value = resp
        }
    }

    fun getVideoByComment() {
        GlobalScope.launch(Dispatchers.Main) {
            val resp = videoRepo.getVideoByComment(100)
            rvData.value = resp
        }
    }

    fun getVideoByCollect() {
        GlobalScope.launch(Dispatchers.Main) {
            val resp = videoRepo.getVideoByCollect(100)
            rvData.value = resp
        }
    }



}