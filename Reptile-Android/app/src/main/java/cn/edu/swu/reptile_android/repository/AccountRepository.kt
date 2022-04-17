package cn.edu.swu.reptile_android.repository

import android.util.Log
import cn.edu.swu.reptile_android.base.BaseRequest
import cn.edu.swu.reptile_android.base.BaseResponse
import cn.edu.swu.reptile_android.model.entity.Account
import cn.edu.swu.reptile_android.model.entity.User
import cn.edu.swu.reptile_android.net.ApiService
import cn.edu.swu.reptile_android.net.NetApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class AccountRepository {

    private val retrofit = NetApi.getRetrofit()
    private val service: ApiService = retrofit.create(ApiService::class.java)


    suspend fun login(username: String, password: String, checksum: String): BaseResponse<Account> {
        val account = Account(0, username, password)
        val req = BaseRequest(checksum, account)
        return service.login(req)
    }





}