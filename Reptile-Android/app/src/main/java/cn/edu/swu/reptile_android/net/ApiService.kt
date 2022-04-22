package cn.edu.swu.reptile_android.net

import cn.edu.swu.reptile_android.base.BaseRequest
import cn.edu.swu.reptile_android.base.BaseResponse
import cn.edu.swu.reptile_android.model.entity.Account
import cn.edu.swu.reptile_android.model.entity.User
import cn.edu.swu.reptile_android.model.entity.Video
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    /**
     * account
     * */
    @POST("/login")
    suspend fun login(@Body req: BaseRequest<Account>): BaseResponse<Account>

    /**
     * user
     * */

    @GET("/user/inc_follower")
    suspend fun getUserByFollowerInc(@Query("size") size: Int): BaseResponse<List<User>>

    @GET("/user/inc_like")
    suspend fun getUserByLikeInc(@Query("size") size: Int): BaseResponse<List<User>>

    @GET("/user/follower")
    suspend fun getUserByFollower(@Query("size") size: Int): BaseResponse<List<User>>

    @GET("/user/like")
    suspend fun getUserByLike(@Query("size") size: Int): BaseResponse<List<User>>


    /**
     * video
     * */

    @GET("/video/inc_like")
    suspend fun getVideoByLikeInc(@Query("size") size: Int): BaseResponse<List<Video>>

    @GET("/video/inc_comment")
    suspend fun getVideoByCommentInc(@Query("size") size: Int): BaseResponse<List<Video>>

    @GET("/video/inc_collect")
    suspend fun getVideoByCollectInc(@Query("size") size: Int): BaseResponse<List<Video>>

    @GET("/video/like")
    suspend fun getVideoByLike(@Query("size") size: Int): BaseResponse<List<Video>>

    @GET("/video/comment")
    suspend fun getVideoByComment(@Query("size") size: Int): BaseResponse<List<Video>>

    @GET("/video/collect")
    suspend fun getVideoByCollect(@Query("size") size: Int): BaseResponse<List<Video>>
}