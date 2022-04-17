package cn.edu.swu.reptile_android.model.entity

import cn.edu.swu.reptile_android.utils.DataUtil

data class User(
    val id: String,
    val nickname: String,
    val headImg: String,
    val link: String,
    val followerCount: String,
    val likeCount: String,
    val followerIncremental: Int,
    val likeIncremental: Int
)