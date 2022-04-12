package cn.edu.swu.reptile_android.model.dao

import androidx.room.Dao
import androidx.room.Query
import cn.edu.swu.reptile_android.model.entity.Account

@Dao
interface AccountDao {
    @Query("SELECT * FROM account")
    fun getAll(): List<Account>


}