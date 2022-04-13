package cn.edu.swu.reptile_android.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Account(
    @PrimaryKey val id: Int,
    @ColumnInfo(name="username") val username: String,
    @ColumnInfo(name="password") val password: String,
    @ColumnInfo(name="nickname") val nickname: String = "默认用户",
    @ColumnInfo(name="headImg") val headImg: String = "xxxxx"
)