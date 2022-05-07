package cn.edu.swu.reptile_android.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cn.edu.swu.reptile_android.R
import cn.edu.swu.reptile_android.base.BaseResponse
import cn.edu.swu.reptile_android.model.entity.Account
import cn.edu.swu.reptile_android.ui.main.MainActivity
import cn.edu.swu.reptile_android.viewmodel.LoginViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class LoginDefaultFragment : Fragment() {

    lateinit var vm: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login_default, container, false)

        val inUsername: TextInputLayout = view.findViewById(R.id.in_account)
        val inPass: TextInputLayout = view.findViewById(R.id.in_password)

        vm = ViewModelProvider(this).get(LoginViewModel::class.java)

        //观察loginInfo
        val observer = Observer<BaseResponse<Account>>() {
            if (it != null) {
                if (it.code != 200) {
                    Toast.makeText(context, "code: ${it.code}, msg: ${it.msg}", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(context, "Login success !!", Toast.LENGTH_LONG).show()
                    //保存用户数据
                    val sp =
                        context?.getSharedPreferences("account", AppCompatActivity.MODE_PRIVATE)
                    val edit = sp!!.edit()
                    edit.putInt("id", it.data.id)
                    edit.putString("username", it.data.username)
                    edit.putString("password", it.data.password)
                    //如果用户未设置昵称，使用用户名
                    if (it.data.nickname == "默认用户"){
                        edit.putString("nickname", it.data.username)
                    }
                    edit.putString("headImg", it.data.headImg)
                    edit.apply()
                    //跳转
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        vm.loginInfo.observe(viewLifecycleOwner, observer)

        //点击按钮
        view.findViewById<MaterialButton>(R.id.btn_login)?.setOnClickListener {
            val username = inUsername.editText?.text.toString()
            val pass = inPass.editText?.text.toString()

            if (username == "" || pass == "") {
                Toast.makeText(context, "请输入用户名/密码！", Toast.LENGTH_LONG).show()
            } else {
                vm.login(username, pass)
            }
        }

        view.findViewById<MaterialButton>(R.id.btn_wechat)?.setOnClickListener {
            val wechatFragment = LoginWechatFragment()
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment, wechatFragment)
            transaction?.commitNow()
        }

        view.findViewById<MaterialButton>(R.id.btn_phone)?.setOnClickListener {
            val phoneFragment = LoginPhoneFragment()
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment, phoneFragment)
            transaction?.commitNow()
        }

        return view
    }


}