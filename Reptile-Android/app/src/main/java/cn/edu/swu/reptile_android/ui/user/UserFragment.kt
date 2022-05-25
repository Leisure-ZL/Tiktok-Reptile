package cn.edu.swu.reptile_android.ui.user

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.swu.reptile_android.R
import cn.edu.swu.reptile_android.base.BaseResponse
import cn.edu.swu.reptile_android.databinding.ItemRvFragmentUserBinding
import cn.edu.swu.reptile_android.model.entity.User
import cn.edu.swu.reptile_android.ui.base.BaseAdapter
import cn.edu.swu.reptile_android.ui.base.BindingAdapter
import cn.edu.swu.reptile_android.ui.base.DropdownMenu
import cn.edu.swu.reptile_android.utils.DataUtil
import cn.edu.swu.reptile_android.viewmodel.MyViewModel
import cn.edu.swu.reptile_android.viewmodel.UserViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout

class UserFragment : Fragment() {

    lateinit var vm: UserViewModel

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
        val view = inflater.inflate(R.layout.fragment_user, container, false)

        vm = ViewModelProvider(this).get(UserViewModel::class.java)

        //init dropdown
        initDropdownMenu(view)

        //初始化Refresh
        refresh = view.findViewById<View>(R.id.refreshLayout) as RefreshLayout
        initRefresh()

        //init rv
        rv = view.findViewById(R.id.rv)
        initRv()

        //init search
        initSearch(view)

        return view
    }

    private fun initSearch(view: View) {
        val searchBtn: LinearLayout = view.findViewById(R.id.top_search)

        searchBtn.setOnClickListener {
            val intent = Intent(context, UserSearchActivity::class.java)
            startActivity(intent)
        }
    }


    private fun initRefresh() {
        refresh.setOnRefreshListener {
            when (cur) {
                0 -> vm.getUserByFollowerInc()
                1 -> vm.getUserByLikeInc()
                2 -> vm.getUserByFollower()
                3 -> vm.getUserByLike()
            }
        }
    }


    private fun initDropdownMenu(view: View) {
        val dropdownMenu = DropdownMenu(context)
        dropdownMenu.init(view, vm.dropdownData)
        dropdownMenu.setOnItemSelectListener(object : DropdownMenu.OnItemSelectListener {
            override fun onItemSelect(position: Int) {
                cur = position
                refresh.autoRefresh()
            }
            override fun onDismiss() {

            }
        })
    }


    private fun initRv() {
        vm.getUserByFollowerInc()

        val observer = Observer<BaseResponse<List<User>>> {
            refresh.finishRefresh()
            if (it != null) {
                if (it.code != 200) {
                    Toast.makeText(context, "code: ${it.code}, msg: ${it.msg}", Toast.LENGTH_LONG)
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
                            val options = RequestOptions.bitmapTransform(CircleCrop())
                            Glide.with(view)
                                .load(user.headImg)
                                .apply(options)
                                .into(view.findViewById(R.id.iv_head))
                            //粉丝增量
                            view.findViewById<TextView>(R.id.tv_follower_incremental).text =
                                DataUtil.numToString(user.followerIncremental)
                            //因为后端数据传输原因，增量排行时粉丝数和点赞数为未转换格式字符串，需要单独处理
                            if(cur == 0){
                                view.findViewById<TextView>(R.id.tv_follower_count).text =
                                    DataUtil.strNumToString(user.followerCount)
                                view.findViewById<TextView>(R.id.tv_like_count).text =
                                    DataUtil.strNumToString(user.likeCount)
                            }
                        }
                    userRankAdapter.setOnItemClickListener(object :BaseAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            val user = it.data[position]
                            val intent = Intent(context, UserDetailActivity::class.java)
                            intent.putExtra("dest", "user")
                            intent.putExtra("key", "id")
                            intent.putExtra("value", user.id)
                            startActivity(intent)
                        }
                    })
                    rv.adapter = userRankAdapter
                    rv.layoutManager = LinearLayoutManager(context)
                    userRankAdapter.notifyDataSetChanged()
                }
            }
        }
        vm.rvData.observe(viewLifecycleOwner, observer)
    }


}