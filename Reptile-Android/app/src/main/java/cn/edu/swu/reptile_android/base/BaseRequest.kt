package cn.edu.swu.reptile_android.base

data class BaseRequest<T>(
        val checksum: String,
        val data: T
)