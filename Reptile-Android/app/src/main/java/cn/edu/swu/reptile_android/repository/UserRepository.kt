package cn.edu.swu.reptile_android.repository

import cn.edu.swu.reptile_android.base.BaseResponse
import cn.edu.swu.reptile_android.model.entity.User
import cn.edu.swu.reptile_android.net.ApiService
import cn.edu.swu.reptile_android.net.NetApi

class UserRepository {

    private val retrofit = NetApi.getRetrofit()
    private val service: ApiService = retrofit.create(ApiService::class.java)

    suspend fun getUserByFollowerInc(size: Int): BaseResponse<List<User>> {
        return service.getUserByFollowerInc(size);
    }

    suspend fun getUserByLikeInc(size: Int): BaseResponse<List<User>> {
        return service.getUserByLikeInc(size);
    }

    suspend fun getUserByFollower(size: Int): BaseResponse<List<User>> {
        return service.getUserByFollower(size);
    }

    suspend fun getUserByLike(size: Int): BaseResponse<List<User>> {
        return service.getUserByLike(size);
    }

    suspend fun getUser(key: String, value: String): BaseResponse<User> {
        val map: Map<String, String> = mapOf("key" to key, "value" to value)
        return service.getUser(map)
    }

    suspend fun getUserByVague(string: String): BaseResponse<List<User>>{
        return service.getUserByVague(string)
    }


}