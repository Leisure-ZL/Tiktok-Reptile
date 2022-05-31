package cn.edu.swu.reptile_android.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.edu.swu.reptile_android.R
import cn.edu.swu.reptile_android.base.BaseResponse
import cn.edu.swu.reptile_android.model.entity.HomeFun
import cn.edu.swu.reptile_android.model.entity.User
import cn.edu.swu.reptile_android.model.entity.Video
import cn.edu.swu.reptile_android.repository.UserRepository
import cn.edu.swu.reptile_android.repository.VideoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    /**
     * funList Data
     * */
    val funData: List<HomeFun> by lazy {
        listOf(HomeFun(R.drawable.ic_user_rank, "达人榜"), HomeFun(R.drawable.ic_video_rank, "热播榜"),
        HomeFun(R.drawable.ic_collect, "我的收藏") ,HomeFun(R.drawable.ic_tiktok, "我的抖音"))
    }


    /**
    * userRank Data
    * */
    private val userRepo = UserRepository()

    private val _userData: MutableLiveData<BaseResponse<List<User>>> = MutableLiveData()
    val userData: MutableLiveData<BaseResponse<List<User>>>
        get() = _userData

    fun getUserByFollowerInc() {
        viewModelScope.launch(Dispatchers.Main) {
            val resp = userRepo.getUserByFollowerInc(5) //默认获取5个
            userData.value = resp
        }
    }

    /**
     * videoRank Data
     * */
    private val videoRepo = VideoRepository()

    private val _videoData: MutableLiveData<BaseResponse<List<Video>>> = MutableLiveData()
    val videoData: MutableLiveData<BaseResponse<List<Video>>>
        get() = _videoData

    fun getVideoByLikeInc() {
        viewModelScope.launch(Dispatchers.Main) {
            val resp = videoRepo.getVideoByLikeInc(5)
            videoData.value = resp
        }
    }

}