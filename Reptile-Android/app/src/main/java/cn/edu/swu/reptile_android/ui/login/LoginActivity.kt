package cn.edu.swu.reptile_android.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.edu.swu.reptile_android.R
import cn.edu.swu.reptile_android.base.BaseResponse
import cn.edu.swu.reptile_android.databinding.ActivityLoginBinding
import cn.edu.swu.reptile_android.model.entity.Account
import cn.edu.swu.reptile_android.ui.base.BaseActivity
import cn.edu.swu.reptile_android.ui.main.MainActivity
import cn.edu.swu.reptile_android.viewmodel.LoginViewModel
import cn.sharesdk.framework.Platform
import cn.sharesdk.framework.PlatformActionListener
import cn.sharesdk.framework.ShareSDK
import cn.sharesdk.wechat.friends.Wechat
import com.google.android.material.button.MaterialButton


class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  setContentView(R.layout.activity_login)
        val dataBinding: ActivityLoginBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_login
        )

        val viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        //与xml双向数据绑定
        dataBinding.lifecycleOwner = this
        dataBinding.loginVM = viewModel

        //观察loginInfo
        val observer = Observer<BaseResponse<Account>>() {
            if (it != null) {
                if (it.code != 200) {
                    Toast.makeText(this, "code: ${it.code}, msg: ${it.msg}", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(this, "Login success !!", Toast.LENGTH_LONG).show()
                    //保存用户数据
                    val sp = getSharedPreferences("account", MODE_PRIVATE)
                    val edit = sp.edit()
                    edit.putString("username", it.data.username)
                    edit.putString("password", it.data.password)
                    edit.putString("nickname", it.data.nickname)
                    edit.putString("headImg", it.data.headImg)
                    edit.apply()
                    //跳转
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        viewModel.loginInfo.observe(this, observer)


        //点击登录按钮
        findViewById<MaterialButton>(R.id.btn_login).setOnClickListener {
            viewModel.login()
        }

    }

    fun clickWechat(view: View) {

        val wechat: Platform = ShareSDK.getPlatform(Wechat.NAME)
        //判定客户端是否可用
        //判定客户端是否可用
        if (wechat.isClientValid()) {
            //客户端可用
        }
        if (wechat.isAuthValid()) {
            wechat.removeAccount(true)
        }
        wechat.setPlatformActionListener(object : PlatformActionListener {
            override fun onComplete(platform: Platform, i: Int, hashMap: HashMap<String?, Any?>?) {
                /*platform.getDb().exportData()获取用户公共信息*/
                Log.d("ShareSDK", "onComplete ---->  登录成功" + platform.getDb().exportData())
                platform.getDb().getUserId()
            }

            override fun onError(platform: Platform?, i: Int, throwable: Throwable) {
                Log.d("ShareSDK", "onError ---->  登录失败$throwable")
                Log.d("ShareSDK", "onError ---->  登录失败" + throwable.stackTrace.toString())
                Log.d("ShareSDK", "onError ---->  登录失败" + throwable.message)
            }

            override fun onCancel(platform: Platform?, i: Int) {
                Log.d("ShareSDK", "onCancel ---->  登录取消")
            }
        })
        wechat.SSOSetting(false)
        wechat.showUser(null)

    }
}