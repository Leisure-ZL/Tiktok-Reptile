package cn.edu.swu.reptile_android.utils

import android.util.Log

object DataUtil {

    public fun numToString(num: Int): String {
        return if(num / 10000 > 0){
            val t = num % 10000 / 1000
            val t2 = num / 10000
            "${t2}.${t}w"
        }else{
            num.toString()
        }
    }

}