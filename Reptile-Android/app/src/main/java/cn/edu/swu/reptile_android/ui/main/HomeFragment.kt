package cn.edu.swu.reptile_android.ui.main

import android.os.Bundle
import android.util.Log
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
import cn.edu.swu.reptile_android.databinding.ItemRvRankUserBinding
import cn.edu.swu.reptile_android.databinding.ItemRvRankVideoBinding
import cn.edu.swu.reptile_android.model.entity.BannerImg
import cn.edu.swu.reptile_android.model.entity.HomeFun
import cn.edu.swu.reptile_android.model.entity.User
import cn.edu.swu.reptile_android.model.entity.Video
import cn.edu.swu.reptile_android.ui.base.BaseAdapter
import cn.edu.swu.reptile_android.ui.base.BindingAdapter
import cn.edu.swu.reptile_android.utils.DataUtil
import cn.edu.swu.reptile_android.viewmodel.HomeViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
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
    private lateinit var videoRankRv: RecyclerView

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


        //初始化videoRank
        videoRankRv = view.findViewById(R.id.rv_rank_video)
        initVideoRank()

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

        val funAdapter = BaseAdapter(R.layout.item_rv_home_fun, data) { view, homeFun ->
            view.findViewById<TextView>(R.id.tv_title).text = homeFun.title
            Glide.with(view).load(homeFun.img).into(view.findViewById(R.id.iv_icon))
        }

        funAdapter.setOnItemClickListener(object: BaseAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.d("tag",position.toString())
                //TODO:to intent
            }
        })
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
                   val userRankAdapter = BindingAdapter(R.layout.item_rv_rank_user, it.data) {
                       view, user ->
                       val binding: ItemRvRankUserBinding? = DataBindingUtil.getBinding(view)
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
                    userRankRv.adapter = userRankAdapter
                    userRankRv.layoutManager = LinearLayoutManager(context)
                    userRankAdapter.notifyDataSetChanged()
                }
            }
        }
        vm.userData.observe(viewLifecycleOwner,observer)
    }

    private fun initVideoRank() {
        vm.getVideoByLikeInc()

        val observer = Observer<BaseResponse<List<Video>>> {
            if (it != null) {
                if(it.code != 200){
                    Toast.makeText(context, "code: ${it.code}, msg: ${it.msg}", Toast.LENGTH_LONG).show()
                }else{
                    val videoRankAdapter = BindingAdapter(R.layout.item_rv_rank_video, it.data) {
                            view, video ->
                        val binding: ItemRvRankVideoBinding? = DataBindingUtil.getBinding(view)
                        if (binding != null) {
                            binding.video = video
                            binding.executePendingBindings()
                        }
                        //增量
//                        view.findViewById<TextView>(R.id.tv_video_inc).text =
//                            DataUtil.numToString(video.likeIncremental)

                    }
                    videoRankRv.adapter = videoRankAdapter
                    videoRankRv.layoutManager = LinearLayoutManager(context)
                    videoRankAdapter.notifyDataSetChanged()
                }
            }
        }
        vm.videoData.observe(viewLifecycleOwner,observer)


    }



}
