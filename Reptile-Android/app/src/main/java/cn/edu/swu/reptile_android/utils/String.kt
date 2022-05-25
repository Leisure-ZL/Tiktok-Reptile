package cn.edu.swu.reptile_android.utils

import android.widget.Toast
import cn.edu.swu.reptile_android.base.BaseApplication

/**
 * 扩展String类，弹出Toast显示当前字符串，默认短时间
 * */
fun String.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(BaseApplication.context, this, duration).show()
}

