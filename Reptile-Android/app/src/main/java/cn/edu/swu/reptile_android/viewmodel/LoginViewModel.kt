package cn.edu.swu.reptile_android.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.edu.swu.reptile_android.base.BaseResponse
import cn.edu.swu.reptile_android.model.entity.Account
import cn.edu.swu.reptile_android.repository.AccountRepository
import cn.edu.swu.reptile_android.utils.EncryptionUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginViewModel() : ViewModel() {

    val username: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    val loginInfo: MutableLiveData<BaseResponse<Account>> = MutableLiveData()

    private val accountRepo = AccountRepository();

    fun login() {

        if(username.value.isNullOrBlank() || password.value.isNullOrBlank()){
            Log.d("tag","username or password is null")
        }else{
            GlobalScope.launch(Dispatchers.Main) {
                val resp = accountRepo.login(username.value!!, password.value!!,
                        checksum(username.value!!, password.value!!))

                Log.d("login", resp.toString())
                loginInfo.value = resp
                //TODO:保存用户数据

            }
        }
    }

    private fun checksum(username:String, pass:String): String {
        //SHA-256
        val str = username + pass + EncryptionUtil.SECRET;
        return EncryptionUtil.getHash(str, "SHA-256")
    }
}