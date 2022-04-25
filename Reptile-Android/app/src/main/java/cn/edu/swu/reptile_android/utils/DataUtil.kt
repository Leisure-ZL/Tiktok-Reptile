package cn.edu.swu.reptile_android.utils

import android.util.Log
import java.util.regex.Pattern

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




    public fun checkTel(tel: String): Boolean{
        val p = Pattern.compile("^[1][3,4,5,7,8,9][0-9]{9}\$")
        val matcher = p.matcher(tel)
        return matcher.matches()
    }

}