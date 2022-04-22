package cn.edu.swu.reptile_android.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.edu.swu.reptile_android.R
import cn.edu.swu.reptile_android.ui.setting.SettingActivity

class MyFragment : Fragment() {

    lateinit var nickname: TextView
    lateinit var headImg: ImageView

    lateinit var settingBtn: ImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my, container, false)

        //init setting
        settingBtn = view.findViewById(R.id.iv_setting)
        initSetting()

        //init user data
        initUserData(view)


        return view
    }

    private fun initSetting() {

        settingBtn.setOnClickListener {
            val intent = Intent(activity, SettingActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initUserData(view: View) {
        nickname = view.findViewById(R.id.tv_nickname)
        headImg = view.findViewById(R.id.head_img)
        val sp = activity?.getSharedPreferences("account", Context.MODE_PRIVATE)

        nickname.text = sp?.getString("nickname", "默认")

        view.findViewById<TextView>(R.id.tv_collect_user).text = sp?.getString("user_collect", "0")
        view.findViewById<TextView>(R.id.tv_collect_video).text = sp?.getString("video_collect", "0")
    }


}