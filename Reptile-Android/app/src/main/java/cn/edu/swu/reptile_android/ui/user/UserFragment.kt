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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.swu.reptile_android.R
import cn.edu.swu.reptile_android.base.BaseResponse
import cn.edu.swu.reptile_android.databinding.ItemRvFragmentUserBinding
import cn.edu.swu.reptile_android.model.entity.User
import cn.edu.swu.reptile_android.ui.base.BaseAdapter
import cn.edu.swu.reptile_android.ui.base.BindingAdapter
import cn.edu.swu.reptile_android.ui.base.DropdownMenu
import cn.edu.swu.reptile_android.ui.main.DetailActivity
import cn.edu.swu.reptile_android.utils.DataUtil
import cn.edu.swu.reptile_android.viewmodel.UserViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout

class UserFragment : Fragment() {

    private val vm = UserViewModel()

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
//            val evSearch: EditText = view.findViewById(R.id.et_search)
//            val nickname = evSearch.text.toString()
//            val intent = Intent(context, DetailActivity::class.java)
//            intent.putExtra("dest", "user")
//            intent.putExtra("key", "nickname")
//            intent.putExtra("value", nickname)
//            startActivity(intent)
            val intent = Intent(context, UserSearchActivity::class.java)
            startActivity(intent)
        }
    }


    private fun initRefresh() {
        refresh.setRefreshHeader(ClassicsHeader(context))
        refresh.setOnRefreshListener {
            when (cur) {
                0 -> vm.getUserByFollowerInc()
                1 -> vm.getUserByLikeInc()
                2 -> vm.getUserByFollower()
                4 -> vm.getUserByLike()
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
                    0 -> vm.getUserByFollowerInc()
                    1 -> vm.getUserByLikeInc()
                    2 -> vm.getUserByFollower()
                    4 -> vm.getUserByLike()
                }
            }
            override fun onDismiss() {

            }
        })
    }



    private fun initRv() {

        //请求数据
        vm.getUserByFollowerInc()

        val observer = Observer<BaseResponse<List<User>>> {
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
                    userRankAdapter.setOnItemClickListener(object :BaseAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            val user = it.data[position]
                            val intent = Intent(context, DetailActivity::class.java)
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