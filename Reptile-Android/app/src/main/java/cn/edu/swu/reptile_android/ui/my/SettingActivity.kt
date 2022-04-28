package cn.edu.swu.reptile_android.ui.my

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import cn.edu.swu.reptile_android.R
import cn.edu.swu.reptile_android.ui.base.BaseActivity
import cn.edu.swu.reptile_android.ui.login.LoginActivity

class SettingActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        findViewById<TextView>(R.id.top_bar_title).text = "设置"


    }

    fun clickBack(view: View) {
        onBackPressed()
    }

    fun exitLogin(view: View) {
        val intent = Intent()
        intent.action = "exit_app"
        sendBroadcast(intent)
        val intent2 = Intent(this, LoginActivity::class.java)
        startActivity(intent2)
    }
}