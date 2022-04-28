package cn.edu.swu.reptile_android.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.swu.reptile_android.R
import cn.edu.swu.reptile_android.base.BaseResponse
import cn.edu.swu.reptile_android.databinding.ItemRvFragmentUserBinding
import cn.edu.swu.reptile_android.databinding.ItemRvFragmentVideoBinding
import cn.edu.swu.reptile_android.model.entity.User
import cn.edu.swu.reptile_android.model.entity.Video
import cn.edu.swu.reptile_android.ui.base.BaseActivity
import cn.edu.swu.reptile_android.ui.base.BaseAdapter
import cn.edu.swu.reptile_android.ui.base.BindingAdapter
import cn.edu.swu.reptile_android.utils.DataUtil
import cn.edu.swu.reptile_android.viewmodel.UserViewModel
import cn.edu.swu.reptile_android.viewmodel.VideoViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
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

        refresh.finishRefresh(1000)

        when(intent.getIntExtra("arg", 0)){
            0 -> loadUserRank()
            1 -> loadVideoRank()
        }
    }




    fun clickBack(view: View){
        onBackPressed()
    }

    private fun loadVideoRank() {
        findViewById<TextView>(R.id.top_bar_title).text = "热播榜"

        val vm = VideoViewModel()
        vm.getVideoByLikeInc()

        val observer = Observer<BaseResponse<List<Video>>> {
            if (it != null) {
                if (it.code != 200) {
                    Toast.makeText(that, "code: ${it.code}, msg: ${it.msg}", Toast.LENGTH_LONG)
                        .show()
                } else {
                    val videoRankAdapter =
                        BindingAdapter(R.layout.item_rv_fragment_video, it.data) { view, video ->
                            val binding: ItemRvFragmentVideoBinding? =
                                DataBindingUtil.getBinding(view)
                            if (binding != null) {
                                binding.video = video
                                binding.executePendingBindings()
                            }
                        }
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
            if (it != null) {
                if (it.code != 200) {
                    Toast.makeText(this, "code: ${it.code}, msg: ${it.msg}", Toast.LENGTH_LONG)
                        .show()
                } else {
                    val userRankAdapter =
                        BindingAdapter(R.layout.item_rv_fragment_user, it.data) { view, user ->
                            val binding: ItemRvFragmentUserBinding? =
                                DataBindingUtil.getBinding(view)
                            if (binding != null) {
                                binding.user = user
                                binding.executePendingBindings()
                            }
                            //头像
                            val roundedCorners = RoundedCorners(60)
                            val options = RequestOptions.bitmapTransform(roundedCorners)
                            Glide.with(view)
                                .load(R.drawable.test_head_user)
                                .apply(options)
                                .into(view.findViewById(R.id.iv_head))
                            //粉丝增量
                            view.findViewById<TextView>(R.id.tv_follower_incremental).text =
                                DataUtil.numToString(user.followerIncremental)
                        }
                    userRankAdapter.setOnItemClickListener(object : BaseAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            val user = it.data[position]
                            val intent = Intent(that, DetailActivity::class.java)
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