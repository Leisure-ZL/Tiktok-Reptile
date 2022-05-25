package cn.edu.swu.reptile_android.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.edu.swu.reptile_android.base.BaseResponse
import cn.edu.swu.reptile_android.model.entity.User
import cn.edu.swu.reptile_android.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val userRepo = UserRepository()


    /**
     * dropdown menu data
     * */
    val dropdownData = listOf("昨日粉丝增量排行", "昨日点赞增量排行", "粉丝总量排行", "点赞总量排行")

    /**
     * rv data
     * */
    private val _rvData: MutableLiveData<BaseResponse<List<User>>> = MutableLiveData()
    val rvData: MutableLiveData<BaseResponse<List<User>>>
        get() = _rvData

    fun getUserByFollowerInc() {
        GlobalScope.launch(Dispatchers.Main) {
            val resp = userRepo.getUserByFollowerInc(100)
            rvData.value = resp
        }
    }

    fun getUserByLikeInc() {
        GlobalScope.launch(Dispatchers.Main) {
            val resp = userRepo.getUserByLikeInc(100)
            rvData.value = resp
        }
    }

    fun getUserByFollower() {
        GlobalScope.launch(Dispatchers.Main) {
            val resp = userRepo.getUserByFollower(100)
            rvData.value = resp
        }
    }

    fun getUserByLike() {
        GlobalScope.launch(Dispatchers.Main) {
            val resp = userRepo.getUserByLike(100)
            rvData.value = resp
        }
    }

    /**
     * search data
     * */

    private val _searchData: MutableLiveData<BaseResponse<List<User>>> = MutableLiveData()
    val searchData: MutableLiveData<BaseResponse<List<User>>>
        get() = _searchData

    fun loadSearchData(string: String){
        GlobalScope.launch(Dispatchers.Main) {
            val resp = userRepo.getUserByVague(string)
            searchData.value = resp
        }
    }


}