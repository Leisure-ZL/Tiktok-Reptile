package cn.edu.swu.reptile_android.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.edu.swu.reptile_android.base.BaseResponse
import cn.edu.swu.reptile_android.model.entity.Account
import cn.edu.swu.reptile_android.model.entity.User
import cn.edu.swu.reptile_android.model.entity.UserCollect
import cn.edu.swu.reptile_android.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {

    private val userRepo = UserRepository()

    private val _collectCount: MutableLiveData<BaseResponse<Int>> = MutableLiveData()
    val collectCount: MutableLiveData<BaseResponse<Int>>
        get() = _collectCount

    private val _collectList: MutableLiveData<BaseResponse<List<UserCollect>>> = MutableLiveData()
    val collectList: MutableLiveData<BaseResponse<List<UserCollect>>>
        get() = _collectList


    fun getCollectCount(id: Int){
        viewModelScope.launch(Dispatchers.Main) {
            val resp = userRepo.getCollectCount(id)
            collectCount.value = resp
        }
    }


    fun getCollectList(id: Int){
        viewModelScope.launch(Dispatchers.Main) {
            val resp = userRepo.getCollectList(id)
            collectList.value = resp
        }
    }


}