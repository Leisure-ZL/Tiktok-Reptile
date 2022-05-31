package cn.edu.swu.reptile_android.repository

import cn.edu.swu.reptile_android.base.BaseResponse
import cn.edu.swu.reptile_android.model.entity.User
import cn.edu.swu.reptile_android.model.entity.Video
import cn.edu.swu.reptile_android.net.ApiService
import cn.edu.swu.reptile_android.net.NetApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideoRepository {

    private val retrofit = NetApi.getRetrofit()
    private val service: ApiService = retrofit.create(ApiService::class.java)

    suspend fun getVideoByLikeInc(size: Int): BaseResponse<List<Video>> {
        return withContext(Dispatchers.IO){
            service.getVideoByLikeInc(size)
        }
    }

    suspend fun getVideoByCommentInc(size: Int): BaseResponse<List<Video>> {
        return withContext(Dispatchers.IO){
            service.getVideoByCommentInc(size)
        }
    }

    suspend fun getVideoByCollectInc(size: Int): BaseResponse<List<Video>> {
        return withContext(Dispatchers.IO){
            service.getVideoByCollectInc(size)
        }
    }

    suspend fun getVideoByLike(size: Int): BaseResponse<List<Video>> {
        return withContext(Dispatchers.IO){
            service.getVideoByLike(size)
        }
    }

    suspend fun getVideoByComment(size: Int): BaseResponse<List<Video>> {
        return withContext(Dispatchers.IO){
            service.getVideoByComment(size)
        }
    }

    suspend fun getVideoByCollect(size: Int): BaseResponse<List<Video>> {
        return withContext(Dispatchers.IO){
            service.getVideoByCollect(size)
        }
    }

    suspend fun getVideoByVague(s: String): BaseResponse<List<Video>> {
        return withContext(Dispatchers.IO){
            service.getVideoByVague(s)
        }
    }

}