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

    //确保其它类不能修改，保证封装性，Google推荐做法
    private val _username: MutableLiveData<String> = MutableLiveData()
    val username: MutableLiveData<String>
        get() = _username

//    val password: MutableLiveData<String> = MutableLiveData()


    private val _loginInfo: MutableLiveData<BaseResponse<Account>> = MutableLiveData()
    val loginInfo: MutableLiveData<BaseResponse<Account>>
        get() = _loginInfo

    private val accountRepo = AccountRepository()

    fun login(username: String, pass: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val resp = accountRepo.login(
                username, pass,
                checksum(username, pass)
            )
            loginInfo.value = resp
        }
    }

    private fun checksum(username: String, pass: String): String {
        //SHA-256
        val str = username + pass + EncryptionUtil.SECRET;
        return EncryptionUtil.getHash(str, "SHA-256")
    }
}