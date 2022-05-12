package cn.edu.swu.reptile_android.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    val collectCount: MutableLiveData<BaseResponse<Int>> = MutableLiveData()
    val collectList: MutableLiveData<BaseResponse<List<UserCollect>>> = MutableLiveData()

    fun getCollectCount(id: Int){
        GlobalScope.launch(Dispatchers.Main) {
            val resp = userRepo.getCollectCount(id)
            collectCount.value = resp
        }
    }


    fun getCollectList(id: Int){
        GlobalScope.launch(Dispatchers.Main) {
            val resp = userRepo.getCollectList(id)
            collectList.value = resp
        }
    }


}