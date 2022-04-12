package cn.edu.swu.reptile_android.net

import cn.edu.swu.reptile_android.base.BaseResponse
import cn.edu.swu.reptile_android.model.entity.Account
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("/login")
    suspend fun login(@Field("username") name: String ,
                      @Field("password") password: String,
                      @Field("checksum") checksum: String): BaseResponse<Account>



}