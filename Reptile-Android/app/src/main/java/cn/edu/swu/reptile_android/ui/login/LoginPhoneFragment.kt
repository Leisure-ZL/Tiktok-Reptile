package cn.edu.swu.reptile_android.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cn.edu.swu.reptile_android.R
import cn.edu.swu.reptile_android.ui.main.MainActivity
import cn.edu.swu.reptile_android.utils.DataUtil
import cn.edu.swu.reptile_android.viewmodel.LoginViewModel
import cn.smssdk.EventHandler
import cn.smssdk.SMSSDK
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginPhoneFragment : Fragment() {

    lateinit var eh: EventHandler

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
        val loginBtn:MaterialButton = view.findViewById(R.id.btn_login)

        eh = object :EventHandler() {
            override fun afterEvent(event: Int, res: Int, data: Any?) {
                if(res == SMSSDK.RESULT_COMPLETE){
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show()
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                    }
                }else if(event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    Toast.makeText(context, "发送成功", Toast.LENGTH_SHORT).show()
                }
            }

        }

        //注册一个事件回调监听，用于处理SMSSDK接口请求的结果
        SMSSDK.registerEventHandler(eh)

        verityBtn.setOnClickListener{
            val phone = etPhone.text.toString()

            if(phone == ""){
                Toast.makeText(context, "请输入手机号", Toast.LENGTH_SHORT).show()
            }else{
                if(DataUtil.checkTel(phone)){
                    SMSSDK.getVerificationCode("86", phone);
                }else{
                    Toast.makeText(context, "请输入正确手机号", Toast.LENGTH_SHORT).show()
                }
            }
        }

        loginBtn.setOnClickListener {
            val verity = etVerity.text.toString()
            val phone = etPhone.text.toString()

            if(verity == ""){
                Toast.makeText(context, "请输入验证码", Toast.LENGTH_SHORT).show()
            }else{
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