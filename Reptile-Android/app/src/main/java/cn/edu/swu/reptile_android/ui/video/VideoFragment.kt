package cn.edu.swu.reptile_android.ui.video

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import cn.edu.swu.reptile_android.ui.base.BaseAdapter
import cn.edu.swu.reptile_android.ui.base.BaseApplication
import cn.edu.swu.reptile_android.ui.base.BindingAdapter
import cn.edu.swu.reptile_android.ui.base.DropdownMenu
import cn.edu.swu.reptile_android.utils.DataUtil
import cn.edu.swu.reptile_android.viewmodel.UserViewModel
import cn.edu.swu.reptile_android.viewmodel.VideoViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout

class VideoFragment : Fragment() {

    val that = this

    private val vm = VideoViewModel()

    private lateinit var refresh: RefreshLayout
    private var cur: Int = 0

    lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_video, container, false)

        //init dropdown
        initDropdownMenu(view)

        //初始化Refresh
        refresh = view.findViewById<View>(R.id.refreshLayout) as RefreshLayout
        initRefresh()

        //init rv
        rv = view.findViewById(R.id.rv)
        initRv()


        return view
    }

    private fun initRefresh() {
        refresh.setRefreshHeader(ClassicsHeader(context))
        refresh.setOnRefreshListener {
            when (cur) {
                0 -> vm.getVideoByLikeInc()
                1 -> vm.getVideoByCommentInc()
                2 -> vm.getVideoByCollectInc()
                3 -> vm.getVideoByLike()
                4 -> vm.getVideoByComment()
                5 -> vm.getVideoByCollect()
            }
            refresh.finishRefresh(1000)
        }
    }


    private fun initDropdownMenu(view: View) {
        val dropdownMenu = DropdownMenu(context)
        dropdownMenu.init(view, vm.dropdownData)
        dropdownMenu.setOnItemSelectListener(object : DropdownMenu.OnItemSelectListener {
            override fun onItemSelect(position: Int) {
                cur = position
                when (position) {
                    0 -> vm.getVideoByLikeInc()
                    1 -> vm.getVideoByCommentInc()
                    2 -> vm.getVideoByCollectInc()
                    3 -> vm.getVideoByLike()
                    4 -> vm.getVideoByComment()
                    5 -> vm.getVideoByCollect()
                }
            }
            override fun onDismiss() {

            }
        })
    }

    private fun initRv() {

        //请求数据

        vm.getVideoByLikeInc()

        val observer = Observer<BaseResponse<List<Video>>> {
            if (it != null) {
                if (it.code != 200) {
                    Toast.makeText(context, "code: ${it.code}, msg: ${it.msg}", Toast.LENGTH_LONG)
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
                    //点击item
                    videoRankAdapter.setOnItemClickListener(object : BaseAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            //保存当前需要显示的数据
                            val app = that.activity?.application as BaseApplication
                            app.detailList = it.data
                            val intent = Intent(context, VideoDetailActivity::class.java)
                            //传递位置
                            intent.putExtra("position", position)
                            startActivity(intent)
                        }
                    })
                    rv.adapter = videoRankAdapter
                    rv.layoutManager = LinearLayoutManager(context)
                    videoRankAdapter.notifyDataSetChanged()
                }
            }
        }
        vm.rvData.observe(viewLifecycleOwner, observer)

    }




}