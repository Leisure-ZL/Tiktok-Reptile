package cn.edu.swu.reptile_android.ui.my

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.edu.swu.reptile_android.R
import cn.edu.swu.reptile_android.base.BaseResponse
import cn.edu.swu.reptile_android.viewmodel.MyViewModel

class MyFragment : Fragment() {

    lateinit var nickname: TextView
    lateinit var headImg: ImageView

    lateinit var settingBtn: ImageView

    lateinit var vm: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my, container, false)

        vm = ViewModelProvider(this).get(MyViewModel::class.java)

        //init setting
        settingBtn = view.findViewById(R.id.iv_setting)
        initSetting()

        //init user data
        initUserData(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        val sp = activity?.getSharedPreferences("account", Context.MODE_PRIVATE)
        val userId = sp?.getInt("id", 0)
        vm.getCollectCount(userId!!)
    }

    private fun initSetting() {

        settingBtn.setOnClickListener {
            val intent = Intent(activity, SettingActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initUserData(view: View) {
        val sp = activity?.getSharedPreferences("account", Context.MODE_PRIVATE)
        val userId = sp?.getInt("id", 0)

        vm.getCollectCount(userId!!)

        nickname = view.findViewById(R.id.tv_nickname)
        headImg = view.findViewById(R.id.head_img)
        nickname.text = sp.getString("nickname", "默认")

        view.findViewById<TextView>(R.id.tv_collect_video).text = sp.getString("video_collect", "0")

        //收藏数
        val tvCollect = view.findViewById<TextView>(R.id.tv_collect_user)

        tvCollect.setOnClickListener {
            val intent = Intent(context, MyCollectActivity::class.java)
            intent.putExtra("id", userId)
            startActivity(intent)
        }

        val observer = Observer<BaseResponse<Int>> {
            if (it != null) {
                if (it.code != 200) {
                    Toast.makeText(context, "code: ${it.code}, msg: ${it.msg}", Toast.LENGTH_LONG)
                        .show()
                } else {
                    val collectCount = vm.collectCount.value?.data
                    tvCollect.text = collectCount?.toString()
                }
            }
        }
        vm.collectCount.observe(viewLifecycleOwner, observer)

    }


}