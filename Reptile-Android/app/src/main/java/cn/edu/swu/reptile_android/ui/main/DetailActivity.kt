package cn.edu.swu.reptile_android.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import cn.edu.swu.reptile_android.R
import cn.edu.swu.reptile_android.ui.base.BaseActivity
import cn.edu.swu.reptile_android.ui.user.UserDetailFragment

class DetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val dest = intent.getStringExtra("dest")
        val key = intent.getStringExtra("key")
        val value = intent.getStringExtra("value")

        val transaction = supportFragmentManager.beginTransaction()

        if(dest == "user") {
            findViewById<TextView>(R.id.top_bar_title).text = "详细信息"

            val userDetailFragment = UserDetailFragment.newInstance(key!!, value!!)
            transaction.replace(R.id.fragment, userDetailFragment)
            transaction.commitNow()
        }


    }

    fun clickBack(view: View) {
        onBackPressed()
    }
}