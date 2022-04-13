package cn.edu.swu.reptile_android.net

import cn.edu.swu.reptile_android.base.BaseRequest
import cn.edu.swu.reptile_android.base.BaseResponse
import cn.edu.swu.reptile_android.model.entity.Account
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST("/login")
    suspend fun login(@Body req: BaseRequest<Account>): BaseResponse<Account>



}