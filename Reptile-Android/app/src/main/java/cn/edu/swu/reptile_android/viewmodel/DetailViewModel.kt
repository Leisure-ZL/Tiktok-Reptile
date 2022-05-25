package cn.edu.swu.reptile_android.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.edu.swu.reptile_android.base.BaseResponse
import cn.edu.swu.reptile_android.model.entity.User
import cn.edu.swu.reptile_android.model.entity.UserCollect
import cn.edu.swu.reptile_android.repository.UserRepository
import cn.edu.swu.reptile_android.utils.EncryptionUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {
    private val userRepo = UserRepository()

    /**
     * user
     * */
    private val _userData: MutableLiveData<BaseResponse<User>> = MutableLiveData()
    val userData: MutableLiveData<BaseResponse<User>>
        get() = _userData

    fun getUser(key: String, value: String){
        GlobalScope.launch(Dispatchers.Main) {
            val resp = userRepo.getUser(key, value)
            userData.value = resp
        }
    }

    /**
     * collect user
     * */

    private val _collectData: MutableLiveData<BaseResponse<String>> = MutableLiveData()
    val collectData: MutableLiveData<BaseResponse<String>>
        get() = _collectData

    private val _isCollect: MutableLiveData<BaseResponse<Boolean>> = MutableLiveData()
    val isCollect: MutableLiveData<BaseResponse<Boolean>>
        get() = _isCollect


    fun collectUser(userId: Int, collectId: Int){
        val check = checksum(userId, collectId)
        GlobalScope.launch(Dispatchers.Main) {
            val resp = userRepo.collectUser(userId, collectId, check)
            collectData.value = resp
        }
    }

    fun unCollectUser(userId: Int, collectId: Int){
        val check = checksum(userId, collectId)
        GlobalScope.launch(Dispatchers.Main) {
            val resp = userRepo.unCollectUser(userId, collectId, check)
            collectData.value = resp
        }
    }


    private fun checksum(userId: Int, collectId: Int): String {
        //SHA-256
        val str = userId.toString() + collectId.toString() + EncryptionUtil.SECRET
        return EncryptionUtil.getHash(str, "SHA-256")
    }


    //查询是否被收藏
    public fun isCollect(userId: Int, collectId: Int){
        GlobalScope.launch(Dispatchers.Main) {
            val resp = userRepo.isCollect(userId, collectId)
            isCollect.value = resp
        }
    }


    /**
     * video
     * */


}