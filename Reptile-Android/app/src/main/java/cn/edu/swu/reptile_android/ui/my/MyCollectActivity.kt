package cn.edu.swu.reptile_android.ui.my

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.swu.reptile_android.R
import cn.edu.swu.reptile_android.base.BaseResponse
import cn.edu.swu.reptile_android.databinding.ItemRvCollectUserBinding
import cn.edu.swu.reptile_android.model.entity.UserCollect
import cn.edu.swu.reptile_android.ui.base.BaseActivity
import cn.edu.swu.reptile_android.ui.base.BaseAdapter
import cn.edu.swu.reptile_android.ui.base.BindingAdapter
import cn.edu.swu.reptile_android.ui.user.UserDetailActivity
import cn.edu.swu.reptile_android.viewmodel.MyViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class MyCollectActivity : BaseActivity() {

    lateinit var rv: RecyclerView
    val that = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_collect)

        findViewById<TextView>(R.id.top_bar_title).text = "我的收藏"

        rv = findViewById(R.id.rv)

        val id = intent.getIntExtra("id", 0)

        val vm = ViewModelProvider(this).get(MyViewModel::class.java)
        vm.getCollectList(id)

        val observer = Observer<BaseResponse<List<UserCollect>>> {
            if (it != null) {
                if (it.code != 200) {
                    Toast.makeText(this, "code: ${it.code}, msg: ${it.msg}", Toast.LENGTH_LONG)
                        .show()
                } else {
                    val userRankAdapter =
                        BindingAdapter(R.layout.item_rv_collect_user, it.data) { view, user ->
                            val binding: ItemRvCollectUserBinding? =
                                DataBindingUtil.getBinding(view)
                            if (binding != null) {
                                binding.user = user
                                binding.executePendingBindings()
                            }
                            //头像
                            val options = RequestOptions.bitmapTransform(CircleCrop())
                            Glide.with(view)
                                .load(user.headImg)
                                .apply(options)
                                .into(view.findViewById(R.id.iv_head))
                        }
                    userRankAdapter.setOnItemClickListener(object : BaseAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            val user = it.data[position]
                            val intent = Intent(that, UserDetailActivity::class.java)
                            intent.putExtra("dest", "user")
                            intent.putExtra("key", "id")
                            intent.putExtra("value", user.collectUserId.toString())
                            startActivity(intent)
                        }
                    })
                    rv.adapter = userRankAdapter
                    rv.layoutManager = LinearLayoutManager(this)
                    userRankAdapter.notifyDataSetChanged()
                }
            }
        }
        vm.collectList.observe(this, observer)

    }

    fun clickBack(view: View) {
        onBackPressed()
    }

}