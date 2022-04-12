package cn.edu.swu.reptile_android.base

/**
* 响应基类
 */

data class BaseResponse<T> (
    val code: Int,
    val msg: String,
    val checksum: String,
    val data: T
)