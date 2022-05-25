package cn.edu.swu.reptile_android.ui.main

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
import cn.edu.swu.reptile_android.databinding.*
import cn.edu.swu.reptile_android.model.entity.User
import cn.edu.swu.reptile_android.model.entity.Video
import cn.edu.swu.reptile_android.ui.base.BaseActivity
import cn.edu.swu.reptile_android.ui.base.BaseAdapter
import cn.edu.swu.reptile_android.base.BaseApplication
import cn.edu.swu.reptile_android.ui.base.BindingAdapter
import cn.edu.swu.reptile_android.ui.user.UserDetailActivity
import cn.edu.swu.reptile_android.ui.video.VideoDetailActivity
import cn.edu.swu.reptile_android.viewmodel.UserViewModel
import cn.edu.swu.reptile_android.viewmodel.VideoViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.scwang.smart.refresh.layout.api.RefreshLayout

class RankActivity : BaseActivity() {
    val that = this
    lateinit var rv: RecyclerView
    lateinit var refresh: RefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rank)


        rv = findViewById(R.id.rv)
        refresh = findViewById<View>(R.id.refreshLayout) as RefreshLayout

        //执行刷新
        refresh.autoRefresh()

        refresh.setOnRefreshListener {
            when(intent.getIntExtra("arg", 0)){
                0 -> loadUserRank()
                1 -> loadVideoRank()
            }
        }

    }

    fun clickBack(view: View){
        onBackPressed()
    }

    private fun loadVideoRank() {
        findViewById<TextView>(R.id.top_bar_title).text = "热播榜"
        val vm = ViewModelProvider(this).get(VideoViewModel::class.java)
        vm.getVideoByLikeInc()

        val observer = Observer<BaseResponse<List<Video>>> {
            refresh.finishRefresh()
            if (it != null) {
                if (it.code != 200) {
                    Toast.makeText(that, "code: ${it.code}, msg: ${it.msg}", Toast.LENGTH_LONG)
                        .show()
                } else {
                    val videoRankAdapter =
                        BindingAdapter(R.layout.item_rv_more_video, it.data) { view, video ->
                            val binding: ItemRvMoreVideoBinding? =
                                DataBindingUtil.getBinding(view)
                            if (binding != null) {
                                binding.video = video
                                binding.executePendingBindings()
                            }
                        }
                    //点击item
                    videoRankAdapter.setOnItemClickListener(object : BaseAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            //保存当前需要显示的数据
                            val app = application as BaseApplication
                            app.detailList = it.data
                            val intent = Intent(that, VideoDetailActivity::class.java)
                            //传递位置
                            intent.putExtra("position", position)
                            startActivity(intent)
                        }
                    })
                    rv.adapter = videoRankAdapter
                    rv.layoutManager = LinearLayoutManager(this)
                    videoRankAdapter.notifyDataSetChanged()
                }
            }
        }
        vm.rvData.observe(this, observer)
    }

    private fun loadUserRank() {
        findViewById<TextView>(R.id.top_bar_title).text = "达人榜"
        val vm = UserViewModel()
        vm.getUserByFollowerInc()

        val observer = Observer<BaseResponse<List<User>>> {
            refresh.finishRefresh()
            if (it != null) {
                if (it.code != 200) {
                    Toast.makeText(this, "code: ${it.code}, msg: ${it.msg}", Toast.LENGTH_LONG)
                        .show()
                } else {
                    val userRankAdapter =
                        BindingAdapter(R.layout.item_rv_more_user, it.data) { view, user ->
                            val binding: ItemRvMoreUserBinding? =
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
                            intent.putExtra("value", user.id)
                            startActivity(intent)
                        }
                    })
                    rv.adapter = userRankAdapter
                    rv.layoutManager = LinearLayoutManager(this)
                    userRankAdapter.notifyDataSetChanged()
                }
            }
        }
        vm.rvData.observe(this, observer)
    }


}