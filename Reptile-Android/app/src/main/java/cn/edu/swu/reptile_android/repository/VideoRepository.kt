package cn.edu.swu.reptile_android.repository

import cn.edu.swu.reptile_android.base.BaseResponse
import cn.edu.swu.reptile_android.model.entity.User
import cn.edu.swu.reptile_android.model.entity.Video
import cn.edu.swu.reptile_android.net.ApiService
import cn.edu.swu.reptile_android.net.NetApi

class VideoRepository {

    private val retrofit = NetApi.getRetrofit()
    private val service: ApiService = retrofit.create(ApiService::class.java)

    suspend fun getVideoByLikeInc(size: Int): BaseResponse<List<Video>> {
        return service.getVideoByLikeInc(size)
    }

    suspend fun getVideoByCommentInc(size: Int): BaseResponse<List<Video>> {
        return service.getVideoByCommentInc(size)
    }

    suspend fun getVideoByCollectInc(size: Int): BaseResponse<List<Video>> {
        return service.getVideoByCollectInc(size)
    }

    suspend fun getVideoByLike(size: Int): BaseResponse<List<Video>> {
        return service.getVideoByLike(size)
    }

    suspend fun getVideoByComment(size: Int): BaseResponse<List<Video>> {
        return service.getVideoByComment(size)
    }

    suspend fun getVideoByCollect(size: Int): BaseResponse<List<Video>> {
        return service.getVideoByCollect(size)
    }

    suspend fun getVideoByVague(s: String): BaseResponse<List<Video>> {
        return service.getVideoByVague(s)
    }

}