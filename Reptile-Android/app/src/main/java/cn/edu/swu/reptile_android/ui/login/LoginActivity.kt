package cn.edu.swu.reptile_android.ui.login


import android.os.Bundle

import cn.edu.swu.reptile_android.R

import cn.edu.swu.reptile_android.ui.base.BaseActivity



class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
          setContentView(R.layout.activity_login)

        val defaultFragment = LoginDefaultFragment()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment, defaultFragment)
        transaction.commitNow()
    }


}