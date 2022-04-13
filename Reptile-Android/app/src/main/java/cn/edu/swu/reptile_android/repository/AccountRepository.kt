package cn.edu.swu.reptile_android.repository

import android.util.Log
import cn.edu.swu.reptile_android.base.BaseRequest
import cn.edu.swu.reptile_android.base.BaseResponse
import cn.edu.swu.reptile_android.model.entity.Account
import cn.edu.swu.reptile_android.net.ApiService
import cn.edu.swu.reptile_android.net.NetApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class AccountRepository {

    private val retrofit = NetApi.getRetrofit()

    suspend fun login(username: String, password: String, checksum: String): BaseResponse<Account> {
        val service = retrofit.create(ApiService::class.java)
        val account = Account(0, username, password)
        val req = BaseRequest(checksum, account)

        return service.login(req)
    }



}