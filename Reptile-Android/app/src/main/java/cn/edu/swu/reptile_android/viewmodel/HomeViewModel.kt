package cn.edu.swu.reptile_android.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.edu.swu.reptile_android.R
import cn.edu.swu.reptile_android.base.BaseResponse
import cn.edu.swu.reptile_android.model.entity.HomeFun
import cn.edu.swu.reptile_android.model.entity.User
import cn.edu.swu.reptile_android.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    //funList Data
    val funData: List<HomeFun> by lazy {
        listOf(HomeFun(R.drawable.ic_user_rank, "达人榜"), HomeFun(R.drawable.ic_video_rank, "热播榜"))
    }


    private val userRepo = UserRepository()


    //userRank Data
    val userData: MutableLiveData<BaseResponse<List<User>>> = MutableLiveData()

    fun getUserByFollowerInc() {
        GlobalScope.launch(Dispatchers.Main) {
            val resp = userRepo.getUserByFollowerInc()
            userData.value = resp
        }

    }


//    fun getUserRank(): List<User> {
//
//    }







}