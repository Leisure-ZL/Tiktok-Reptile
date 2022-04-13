package cn.edu.swu.reptile_android.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.edu.swu.reptile_android.R
import cn.edu.swu.reptile_android.base.BaseResponse
import cn.edu.swu.reptile_android.databinding.ActivityLoginBinding
import cn.edu.swu.reptile_android.model.entity.Account
import cn.edu.swu.reptile_android.ui.main.MainActivity
import cn.edu.swu.reptile_android.viewmodel.LoginViewModel
import com.google.android.material.button.MaterialButton

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_login)
        val dataBinding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        val viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        //与xml双向数据绑定
        dataBinding.lifecycleOwner = this
        dataBinding.loginVM = viewModel

        //观察loginInfo
        val observer = Observer<BaseResponse<Account>>() {
            if (it != null) {
                if(it.code != 200){
                    Toast.makeText(this, "code: ${it.code}, msg: ${it.msg}", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this, "Login success !!", Toast.LENGTH_LONG).show()
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
}