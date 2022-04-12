package cn.edu.swu.reptile_android.repository

import android.util.Log
import cn.edu.swu.reptile_android.net.ApiService
import cn.edu.swu.reptile_android.net.NetApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AccountRepository {

    private val retrofit = NetApi.getRetrofit()

    fun login(username: String, password: String, checksum: String) {
        val service = retrofit.create(ApiService::class.java)

        GlobalScope.launch {

            val resp = service.login(username, password, checksum)
            Log.d("tag",resp.toString())

        }
    }



}