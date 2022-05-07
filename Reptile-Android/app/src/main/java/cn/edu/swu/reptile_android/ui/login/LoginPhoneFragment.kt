package cn.edu.swu.reptile_android.ui.login

import android.R.attr
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import cn.edu.swu.reptile_android.R
import cn.edu.swu.reptile_android.base.BaseResponse
import cn.edu.swu.reptile_android.model.entity.Account
import cn.edu.swu.reptile_android.ui.main.MainActivity
import cn.edu.swu.reptile_android.utils.DataUtil
import cn.edu.swu.reptile_android.viewmodel.LoginViewModel
import cn.smssdk.EventHandler
import cn.smssdk.SMSSDK
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.mob.MobSDK
import org.json.JSONException
import org.json.JSONObject


class LoginPhoneFragment : Fragment() {

    lateinit var eh: EventHandler
    lateinit var vm: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login_phone, container, false)

        val vm = LoginViewModel()

        val etPhone = view.findViewById<TextInputEditText>(R.id.et_phone)
        val etVerity = view.findViewById<TextInputEditText>(R.id.et_verity)

        val verityBtn: MaterialButton = view.findViewById(R.id.btn_verification)
        val loginBtn: MaterialButton = view.findViewById(R.id.btn_login)

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


        MobSDK.submitPolicyGrantResult(true, null)

        eh = object : EventHandler() {
            override fun afterEvent(event: Int, res: Int, data: Any?) {
                if (res == SMSSDK.RESULT_COMPLETE) {
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        activity?.runOnUiThread(Runnable {
                            //login
                            val username = etPhone.text.toString()
                            val pass = "123456" //默认手机验证登录密码为123456
                            vm.login(username, pass)    //进行服务端注册
                        })
                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //成功发送验证码
                        activity?.runOnUiThread(Runnable {
                            Toast.makeText(
                                context,
                                "发送成功",
                                Toast.LENGTH_SHORT
                            ).show()
                        })
                    }
                } else{
                    Log.i("tag", data.toString())
                    val throwable = attr.data as Throwable
                    throwable.printStackTrace()
                    Log.i("tag", throwable.toString())
                    try {
                        val obj = JSONObject(throwable.message!!)
                        val des = obj.optString("detail")
                        if (!TextUtils.isEmpty(des)) {
                            activity?.runOnUiThread(Runnable {
                                Toast.makeText(
                                    context,
                                    des,
                                    Toast.LENGTH_SHORT
                                ).show()
                            })
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }

        }

        //注册一个事件回调监听，用于处理SMSSDK接口请求的结果
        SMSSDK.registerEventHandler(eh)

        verityBtn.setOnClickListener {
            val phone = etPhone.text.toString()

            if (phone == "") {
                Toast.makeText(context, "请输入手机号", Toast.LENGTH_SHORT).show()
            } else {
                if (DataUtil.checkTel(phone)) {
                    SMSSDK.getVerificationCode("86", phone);
                } else {
                    Toast.makeText(context, "请输入正确手机号", Toast.LENGTH_SHORT).show()
                }
            }
        }

        loginBtn.setOnClickListener {
            val verity = etVerity.text.toString()
            val phone = etPhone.text.toString()

            if (verity == "") {
                Toast.makeText(context, "请输入验证码", Toast.LENGTH_SHORT).show()
            } else {
                SMSSDK.submitVerificationCode("86", phone, verity)
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        SMSSDK.unregisterEventHandler(eh);
    }

}