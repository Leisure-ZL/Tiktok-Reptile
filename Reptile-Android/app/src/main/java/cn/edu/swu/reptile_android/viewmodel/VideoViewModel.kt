package cn.edu.swu.reptile_android.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.edu.swu.reptile_android.base.BaseResponse
import cn.edu.swu.reptile_android.model.entity.User
import cn.edu.swu.reptile_android.model.entity.Video
import cn.edu.swu.reptile_android.repository.VideoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class VideoViewModel : ViewModel(){

    private val videoRepo = VideoRepository()

    /**
     * dropdown menu data
     * */
    val dropdownData = listOf("昨日点赞增量排行", "昨日评论增量排行", "昨日收藏增量排行",
        "点赞总量排行", "评论总量排行", "收藏总量排行")

    /**
     * rv data
     * */
    private val _rvData: MutableLiveData<BaseResponse<List<Video>>> = MutableLiveData()
    val rvData: MutableLiveData<BaseResponse<List<Video>>>
        get() = _rvData

    fun getVideoByLikeInc() {
        viewModelScope.launch(Dispatchers.Main) {
            val resp = videoRepo.getVideoByLikeInc(100)
            rvData.value = resp
        }
    }

    fun getVideoByCommentInc() {
        viewModelScope.launch(Dispatchers.Main) {
            val resp = videoRepo.getVideoByCommentInc(100)
            rvData.value = resp
        }
    }

    fun getVideoByCollectInc() {
        viewModelScope.launch(Dispatchers.Main) {
            val resp = videoRepo.getVideoByCollectInc(100)
            rvData.value = resp
        }
    }

    fun getVideoByLike() {
        viewModelScope.launch(Dispatchers.Main) {
            val resp = videoRepo.getVideoByLike(100)
            rvData.value = resp
        }
    }

    fun getVideoByComment() {
        viewModelScope.launch(Dispatchers.Main) {
            val resp = videoRepo.getVideoByComment(100)
            rvData.value = resp
        }
    }

    fun getVideoByCollect() {
        viewModelScope.launch(Dispatchers.Main) {
            val resp = videoRepo.getVideoByCollect(100)
            rvData.value = resp
        }
    }

    /**
     * search data
     * */
    private val _searchData: MutableLiveData<BaseResponse<List<Video>>> = MutableLiveData()
    val searchData: MutableLiveData<BaseResponse<List<Video>>>
        get() = _searchData

    fun loadSearchData(string: String){
        viewModelScope.launch(Dispatchers.Main) {
            val resp = videoRepo.getVideoByVague(string)
            searchData.value = resp
        }
    }


}