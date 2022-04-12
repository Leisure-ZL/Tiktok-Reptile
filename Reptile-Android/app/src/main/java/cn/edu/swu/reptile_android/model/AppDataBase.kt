package cn.edu.swu.reptile_android.model

import androidx.room.Database
import cn.edu.swu.reptile_android.model.dao.AccountDao
import cn.edu.swu.reptile_android.model.entity.Account

@Database(entities = [Account::class], version = 1)
abstract class AppDataBase {
    abstract fun accountDao(): AccountDao

}