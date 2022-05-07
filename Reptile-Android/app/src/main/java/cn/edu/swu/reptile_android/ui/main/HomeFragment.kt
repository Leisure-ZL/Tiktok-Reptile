package cn.edu.swu.reptile_android.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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
import cn.edu.swu.reptile_android.ui.base.BaseApplication
import cn.edu.swu.reptile_android.ui.base.BindingAdapter
import cn.edu.swu.reptile_android.ui.my.MyCollectActivity
import cn.edu.swu.reptile_android.ui.video.VideoDetailActivity
import cn.edu.swu.reptile_android.utils.DataUtil
import cn.edu.swu.reptile_android.viewmodel.HomeViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
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

    val that = this

  //  private lateinit var dataBinding: FragmentHomeBinding

    private lateinit var refresh: RefreshLayout
    private lateinit var banner: Banner<BannerImg, BannerImageAdapter<BannerImg>>
    private lateinit var funRv: RecyclerView
    private lateinit var userMore: TextView
    private lateinit var videoMore: TextView

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
        initLineChart()

        //初始化userRank
        userRankRv = view.findViewById(R.id.rv_rank_user)
        userMore = view.findViewById(R.id.tv_user_more)
        initUserRank()


        //初始化videoRank
        videoRankRv = view.findViewById(R.id.rv_rank_video)
        videoMore = view.findViewById(R.id.tv_video_more)
        initVideoRank()

        return view
    }

    private fun initLineChart() {

//        var xLableCount = 7
//        var xRangeMaximum = xLableCount - 1
//
//        val netLineList: List<Map.Entry<*, *>> = ArrayList()
//        val netDateList: List<String> = ArrayList()
//        val lineChart = LineChart(context)
//        LineChartUtils.initChart(lineChart, true, false, false)
//
//        var s: String?
//        val dateFormat1 = SimpleDateFormat("MM-dd")
//        val c: Calendar = Calendar.getInstance()
//        val currentDate: String = dateFormat1.format(c.getTime())
//        netDateList.add(currentDate)
//        for (i in 1..6) {
//            s = formatDatas(i)
//            netDateList.add(s)
//        }
//        Collections.reverse(netDateList)
//
//        val lineFloat = floatArrayOf(11f, 15f, 16f, 17f, 16f, 16f, 12f)
//        for (i in 0 until netDateList.size()) {
////            netLineList.add(new Entry((float) i, (float) Math.random() * 80));
//            netLineList.add(
//                MutableMap.MutableEntry<Any?, Any?>(
//                    i.toFloat(),
//                    lineFloat[i]
//                )
//            )
//        }
//
//        xLableCount = if (netDateList.size + 3 > 7) 7 else netDateList.size + 3
//        xRangeMaximum = xLableCount - 1
//
//        LineChartUtils.setXAxis(lineChart, xLableCount, netDateList.size, xRangeMaximum)
//        LineChartUtils.notifyDataSetChanged(lineChart, netLineList, netDateList)
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
        val bannerImgs: List<BannerImg> = listOf(
            BannerImg("http://www.swu.edu.cn/images/notice/378.jpg"),
            BannerImg("http://www.swu.edu.cn/images/notice/377.jpg")
        )

        banner.addBannerLifecycleObserver(this)
                .setIndicator(CircleIndicator(context))
                .setLoopTime(5000) //滑动间隔时间
                .setAdapter(object : BannerImageAdapter<BannerImg>(bannerImgs) {
                    override fun onBindView(
                        holder: BannerImageHolder?,
                        data: BannerImg?,
                        position: Int,
                        size: Int
                    ) {
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

        funAdapter.setOnItemClickListener(object : BaseAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                var intent = Intent(context, RankActivity::class.java)
                when (position) {
                    0 -> intent.putExtra("arg", 0)
                    1 -> intent.putExtra("arg", 1)
                    2 -> {
                        val sp = activity?.getSharedPreferences("account", Context.MODE_PRIVATE)
                        val userId = sp?.getInt("id", 0)
                        intent = Intent(context, MyCollectActivity::class.java)
                        intent.putExtra("id", userId)
                    }
                }
                startActivity(intent)
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
                   val userRankAdapter = BindingAdapter(R.layout.item_rv_rank_user, it.data) { view, user ->
                       val binding: ItemRvRankUserBinding? = DataBindingUtil.getBinding(view)
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
                   }
                    //点击item
                    userRankAdapter.setOnItemClickListener(object :
                        BaseAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            val user = it.data[position]
                            val intent = Intent(context, DetailActivity::class.java)
                            intent.putExtra("dest", "user")
                            intent.putExtra("key", "id")
                            intent.putExtra("value", user.id)
                            startActivity(intent)
                        }
                    })

                    userRankRv.adapter = userRankAdapter
                    userRankRv.layoutManager = LinearLayoutManager(context)
                    userRankAdapter.notifyDataSetChanged()
                }
            }
        }
        vm.userData.observe(viewLifecycleOwner, observer)

        userMore.setOnClickListener {
            val intent = Intent(context, RankActivity::class.java)
            intent.putExtra("arg", 0)
            startActivity(intent)
        }

    }

    private fun initVideoRank() {
        vm.getVideoByLikeInc()

        val observer = Observer<BaseResponse<List<Video>>> {
            if (it != null) {
                if(it.code != 200){
                    Toast.makeText(context, "code: ${it.code}, msg: ${it.msg}", Toast.LENGTH_LONG).show()
                }else{
                    val videoRankAdapter = BindingAdapter(R.layout.item_rv_rank_video, it.data) { view, video ->
                        val binding: ItemRvRankVideoBinding? = DataBindingUtil.getBinding(view)
                        if (binding != null) {
                            binding.video = video
                            binding.executePendingBindings()
                        }
                        //增量
//                        view.findViewById<TextView>(R.id.tv_video_inc).text =
//                            DataUtil.numToString(video.likeIncremental)

                    }
                    //点击item
                    videoRankAdapter.setOnItemClickListener(object :
                        BaseAdapter.OnItemClickListener {
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
                    videoRankRv.adapter = videoRankAdapter
                    videoRankRv.layoutManager = LinearLayoutManager(context)
                    videoRankAdapter.notifyDataSetChanged()
                }
            }
        }
        vm.videoData.observe(viewLifecycleOwner, observer)

        videoMore.setOnClickListener {
            val intent = Intent(context, RankActivity::class.java)
            intent.putExtra("arg", 1)
            startActivity(intent)
        }

    }



}
