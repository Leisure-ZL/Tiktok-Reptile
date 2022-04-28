package cn.edu.swu.reptile_android.model.entity

data class Video(
    val id: Int,
    val videoName: String,
    val likeNum: String,
    val commentNum: String,
    val collectNum: String,
    val userName: String,
    val url: String,
    val videoUrl: String,
    val likeIncremental: Int,
    val commentIncremental: Int,
    val collectIncremental: Int
)