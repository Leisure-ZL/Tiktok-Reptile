package cn.edu.swu.reptile_android.ui.main

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.swu.reptile_android.R
import cn.edu.swu.reptile_android.base.BaseResponse
import cn.edu.swu.reptile_android.model.entity.Account
import cn.edu.swu.reptile_android.model.entity.BannerImg
import cn.edu.swu.reptile_android.model.entity.HomeFun
import cn.edu.swu.reptile_android.model.entity.User
import cn.edu.swu.reptile_android.ui.main.adapter.FunAdapter
import cn.edu.swu.reptile_android.ui.main.adapter.UserRankAdapter
import cn.edu.swu.reptile_android.viewmodel.HomeViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator


class HomeFragment : Fragment() {

    private val vm = HomeViewModel()

  //  private lateinit var dataBinding: FragmentHomeBinding

    private lateinit var refresh: RefreshLayout
    private lateinit var banner: Banner<BannerImg, BannerImageAdapter<BannerImg>>
    private lateinit var funRv: RecyclerView

    private lateinit var userRankRv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
     //   dataBinding = DataBindingUtil.inflate(inflater,
       //     R.layout.fragment_home, container, false);

      //  val view = dataBinding.root
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        //初始化Refresh
        refresh = view.findViewById<View>(R.id.refreshLayout) as RefreshLayout
        initRefresh()

        //初始化Banner
        banner = view.findViewById(R.id.banner)
        initBanner()

        //初始化功能区
        funRv = view.findViewById(R.id.rv_fun)
        initFunList()

        //初始化今日大盘
        //TODO

        //初始化userRank
        userRankRv = view.findViewById(R.id.rv_rank_user)
        initUserRank()



        return view
    }



    private fun initRefresh() {
        refresh.setRefreshHeader(ClassicsHeader(context))
        refresh.setOnRefreshListener {
            //TODO: refresh data
            //update userRank data
            vm.getUserByFollowerInc()

            refresh.finishRefresh(1000)
        }
    }


    private fun initBanner(){
        val bannerImgs: List<BannerImg> = listOf(BannerImg("http://www.swu.edu.cn/images/notice/378.jpg")
                , BannerImg("http://www.swu.edu.cn/images/notice/377.jpg"))

        banner.addBannerLifecycleObserver(this)
                .setIndicator(CircleIndicator(context))
                .setLoopTime(5000) //滑动间隔时间
                .setAdapter(object: BannerImageAdapter<BannerImg>(bannerImgs) {
                    override fun onBindView(holder: BannerImageHolder?, data: BannerImg?, position: Int, size: Int) {
                        if (holder != null && data != null) {
                            Glide.with(holder.itemView)
                                    .load(data.imgUrl)
                                    .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                                    .into(holder.imageView)
                        }
                    }
                })
    }

    private fun initFunList(){
        val data: List<HomeFun> = vm.funData
        val funAdapter = context?.let { FunAdapter(it, data) }
        funRv.adapter = funAdapter
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.HORIZONTAL
        funRv.layoutManager = layoutManager
    }

    private fun initUserRank() {

        vm.getUserByFollowerInc()

        val observer = Observer<BaseResponse<List<User>>> {
            if (it != null) {
                if(it.code != 200){
                    Toast.makeText(context, "code: ${it.code}, msg: ${it.msg}", Toast.LENGTH_LONG).show()
                }else{
                    val userRankAdapter = context?.let { it1 -> UserRankAdapter(it1, it.data) }
                    userRankRv.adapter = userRankAdapter
                    userRankRv.layoutManager = LinearLayoutManager(context)
                    userRankAdapter?.notifyDataSetChanged()
                }
            }
        }
        vm.userData.observe(viewLifecycleOwner,observer)

    }


}
