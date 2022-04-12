package cn.edu.swu.reptile_android.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.edu.swu.reptile_android.model.entity.Account
import cn.edu.swu.reptile_android.repository.AccountRepository

class LoginViewModel() : ViewModel() {

    val username: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    private val accountRepo = AccountRepository();

    private val checksum: String = checksum()

    fun login() {

        if(username.value.isNullOrBlank() || password.value.isNullOrBlank()){
            Log.d("tag","username/password is null")
        }else{
            accountRepo.login(username.value!!, password.value!!, checksum)
        }
    }

    private fun checksum(): String {
        //TODO: sha256
        return "123456"
    }
}