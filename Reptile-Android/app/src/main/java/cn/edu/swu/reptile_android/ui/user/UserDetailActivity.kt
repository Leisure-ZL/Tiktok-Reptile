package cn.edu.swu.reptile_android.ui.user

import android.os.Bundle
import android.view.View
import android.widget.TextView
import cn.edu.swu.reptile_android.R
import cn.edu.swu.reptile_android.ui.base.BaseActivity

const val TAG_FRAGMENT = "fragment"

class UserDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        val dest = intent.getStringExtra("dest")
        val key = intent.getStringExtra("key")
        val value = intent.getStringExtra("value")

        val transaction = supportFragmentManager.beginTransaction()

        if(dest == "user") {
            findViewById<TextView>(R.id.top_bar_title).text = "详细信息"

           // val userDetailFragment = UserDetailFragment.newInstance(key!!, value!!)

            var fragment = supportFragmentManager.findFragmentByTag(TAG_FRAGMENT)
            if(fragment == null) {
                fragment = UserDetailFragment.newInstance(key!!, value!!)
                transaction.replace(R.id.fragment, fragment, TAG_FRAGMENT)
                transaction.commitNow()
            }
        }
    }


    fun clickBack(view: View) {
        onBackPressed()
    }
}