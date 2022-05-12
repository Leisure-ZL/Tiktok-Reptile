package cn.edu.swu.reptile_android.ui.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import cn.edu.swu.reptile_android.R
import cn.edu.swu.reptile_android.base.BaseResponse
import cn.edu.swu.reptile_android.databinding.FragmentUserDetailBinding
import cn.edu.swu.reptile_android.model.entity.User
import cn.edu.swu.reptile_android.utils.DataUtil
import cn.edu.swu.reptile_android.viewmodel.DetailViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions


private const val ARG_PARAM1 = "key"
private const val ARG_PARAM2 = "value"

class UserDetailFragment : Fragment() {
    private var key: String? = null
    private var value: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            key = it.getString(ARG_PARAM1)
            value = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentUserDetailBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_user_detail, container, false
        )
        binding.executePendingBindings()

        val vm = DetailViewModel()
        //加载User
        vm.getUser(key!!, value!!)

        var mUserId = 0
        var mUserCollectId = 0
        var flag: Boolean?

        val observer = Observer<BaseResponse<User>> {
            if (it != null) {
                if (it.code == -1) {
                    Toast.makeText(context, "code: ${it.code}, msg: ${it.msg}", Toast.LENGTH_LONG)
                        .show()
                    //TODO:错误页装载
                } else if(it.code == 200) {
                    val user = vm.userData.value!!.data
                    binding.user = user
                    //头像
                    val options = RequestOptions.bitmapTransform(CircleCrop())
                    view?.let { it1 ->
                        Glide.with(this)
                            .load(user.headImg)
                            .apply(options)
                            .into(it1.findViewById(R.id.head_img))
                    }
                    //粉丝增量
                    val followerInc = user.let { it1 -> "+${DataUtil.numToString(it1.followerIncremental)}" }
                    view?.findViewById<TextView>(R.id.tv_follower_incremental)?.text = followerInc
                    //点赞增量
                    val likeInc = user.let { it1 -> "+${DataUtil.numToString(it1.likeIncremental)}" }
                    view?.findViewById<TextView>(R.id.tv_like_incremental)?.text  = likeInc
                    //是否已收藏
                    val sp = activity?.getSharedPreferences("account", Context.MODE_PRIVATE)
                    val userId = sp?.getInt("id", 0)!!
                    vm.isCollect(userId, user.id.toInt())
                    //更新数据
                    mUserCollectId = user.id.toInt()
                    mUserId = userId
                    //点击url
                    view?.findViewById<TextView>(R.id.tv_url)?.setOnClickListener {
                        val intent = Intent(context, UserWebActivity::class.java)
                        intent.putExtra("url", "https:${user.link}")
                        startActivity(intent)
                    }
                }else{
                    Toast.makeText(context?.applicationContext, "code: ${it.code}, msg: ${it.msg}", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
        vm.userData.observe(viewLifecycleOwner, observer)

        val observer2 = Observer<BaseResponse<Boolean>> {
            if (it != null) {
                if(it.code == 200) {
                    //是否已收藏
                    val collectBtn: Button? = view?.findViewById(R.id.btn_collect)
                    if(vm.isCollect.value?.data == true){
                        collectBtn?.setTextColor(android.graphics.Color.BLACK)
                        collectBtn?.text = "取消收藏"
                        collectBtn?.setBackgroundResource(R.drawable.shape_uncollect_btn)
                    }else{
                        collectBtn?.setTextColor(android.graphics.Color.WHITE)
                        collectBtn?.text = "收藏"
                        collectBtn?.setBackgroundResource(R.drawable.shape_collect_btn)
                    }
                    flag = vm.isCollect.value?.data
                    //点击收藏Btn
                    collectBtn?.setOnClickListener {
                        if(flag == true){
                            vm.unCollectUser(mUserId, mUserCollectId)
                            flag = false
                        }else{
                            vm.collectUser(mUserId, mUserCollectId)
                            flag = true
                        }
                    }
                }else{
                    Toast.makeText(context?.applicationContext, "code: ${it.code}, msg: ${it.msg}", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
        vm.isCollect.observe(viewLifecycleOwner, observer2)

        val observer3 = Observer<BaseResponse<String>> {
            if (it != null) {
                if(it.code == 200) {
                    val resp = vm.collectData.value
                    if(resp?.code == 200){
                        val collectBtn: Button? = view?.findViewById(R.id.btn_collect)
                        if(resp.data == "collect"){
                            Toast.makeText(context?.applicationContext, "收藏成功！", Toast.LENGTH_SHORT).show()
                            collectBtn?.setTextColor(android.graphics.Color.BLACK)
                            collectBtn?.text = "取消收藏"
                            collectBtn?.setBackgroundResource(R.drawable.shape_uncollect_btn)
                        }else{
                            Toast.makeText(context?.applicationContext, "取消收藏！", Toast.LENGTH_SHORT).show()
                            collectBtn?.setTextColor(android.graphics.Color.WHITE)
                            collectBtn?.text = "收藏"
                            collectBtn?.setBackgroundResource(R.drawable.shape_collect_btn)
                        }
                    }else{
                        Toast.makeText(context?.applicationContext, "收藏失败！", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(context?.applicationContext, "code: ${it.code}, msg: ${it.msg}", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
        vm.collectData.observe(viewLifecycleOwner, observer3)

        return binding.root;
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}