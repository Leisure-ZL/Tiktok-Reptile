package cn.edu.swu.reptile_android.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import cn.edu.swu.reptile_android.R
import cn.edu.swu.reptile_android.databinding.ActivityLoginBinding
import cn.edu.swu.reptile_android.viewmodel.LoginViewModel
import com.google.android.material.button.MaterialButton

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_login)
        val dataBinding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        val viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        dataBinding.lifecycleOwner = this
        dataBinding.loginVM = viewModel

        findViewById<MaterialButton>(R.id.btn_login).setOnClickListener {
            viewModel.login()
        }

    }
}