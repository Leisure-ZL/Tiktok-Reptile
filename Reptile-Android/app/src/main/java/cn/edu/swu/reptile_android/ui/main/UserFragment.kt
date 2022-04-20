package cn.edu.swu.reptile_android.ui.main

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.swu.reptile_android.R
import cn.edu.swu.reptile_android.base.BaseResponse
import cn.edu.swu.reptile_android.databinding.ItemRvFragmentUserBinding
import cn.edu.swu.reptile_android.databinding.ItemRvRankUserBinding
import cn.edu.swu.reptile_android.model.entity.User
import cn.edu.swu.reptile_android.ui.base.BaseAdapter
import cn.edu.swu.reptile_android.ui.base.BindingAdapter
import cn.edu.swu.reptile_android.utils.DataUtil
import cn.edu.swu.reptile_android.viewmodel.UserViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class UserFragment : Fragment() {

    private val vm = UserViewModel()

    lateinit var rv: RecyclerView
    lateinit var maskView: View
    lateinit var tabView: LinearLayout
    lateinit var tabTitel: TextView
    lateinit var tabIcon: ImageView

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

        //init rv
        rv = view.findViewById(R.id.rv)
        initRv()

        maskView = view.findViewById(R.id.mask_view)

        return view
    }


    private fun initDropdownMenu(view: View) {

        tabView = view.findViewById(R.id.ly_dropdown_tab)
        tabTitel = view.findViewById(R.id.tv_dropdown_title)
        tabIcon = view.findViewById(R.id.iv_dropdown_icon)

        tabView.setOnClickListener {
            //角标变化
            tabIcon.setImageResource(R.drawable.ic_down)
            //遮罩层动画
            maskView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.view_mask_enter_anim))
            //弹出popWin
            showPopupWindow(tabView)
        }

    }

    private fun showPopupWindow(tabView: View) {
        val contentView: View = LayoutInflater.from(context).inflate(R.layout.popup_dropdown_menu, null)
        val popWindow = PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT, true)
        popWindow.contentView = contentView

        //RV
        val data = vm.dropdownData
        val dropdownRv: RecyclerView = contentView.findViewById(R.id.rv)
        dropdownRv.layoutManager = LinearLayoutManager(contentView.context)
        val adapter = BaseAdapter(R.layout.item_rv_dropdown, data) { view, s ->
            view.findViewById<TextView>(R.id.tv_item_title).text = s
            if(s == tabTitel.text){ //当前选中的item
                view.findViewById<TextView>(R.id.tv_item_title).setTextColor(Color.BLACK)
                view.findViewById<ImageView>(R.id.iv_item_icon).visibility = View.VISIBLE
            }
        }
        //select item
        adapter.setOnItemClickListener(object: BaseAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                popWindow.dismiss()
                tabTitel.text = data[position]
                //加载数据
                when(position){
                    0 -> vm.getUserByFollowerInc()
                    1 -> vm.getUserByLikeInc()
                    2 -> vm.getUserByFollower()
                    else -> vm.getUserByLike()
                }
            }
        })
        dropdownRv.adapter = adapter

        //弹出动画
        popWindow.animationStyle = R.style.popwin_anim

        //遮罩效果
        maskView.visibility = View.VISIBLE
        popWindow.setOnDismissListener {
            maskView.visibility = View.GONE
            tabIcon.setImageResource(R.drawable.ic_up)
        }

        //弹出窗口
        popWindow.showAsDropDown(tabView)
    }




    private fun initRv() {

        //请求数据

        vm.getUserByFollowerInc()

        val observer = Observer<BaseResponse<List<User>>> {
            if (it != null) {
                if(it.code != 200){
                    Toast.makeText(context, "code: ${it.code}, msg: ${it.msg}", Toast.LENGTH_LONG).show()
                }else{
                    val userRankAdapter = BindingAdapter(R.layout.item_rv_fragment_user, it.data) {
                            view, user ->
                        val binding: ItemRvFragmentUserBinding? = DataBindingUtil.getBinding(view)
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
                    rv.adapter = userRankAdapter
                    rv.layoutManager = LinearLayoutManager(context)
                    userRankAdapter.notifyDataSetChanged()
                }
            }
        }
        vm.rvData.observe(viewLifecycleOwner,observer)

    }




}