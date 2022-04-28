package cn.edu.swu.reptile_android.model.entity

data class UserCollect(
    val id: Int,
    val userId: Int,
    val collectUserId: Int,
    val nickname: String = "",
    val followerCount: String = "",
    val headImg: String = ""
)