package cn.edu.swu.reptile_android.ui.video

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.swu.reptile_android.R
import cn.edu.swu.reptile_android.base.BaseResponse
import cn.edu.swu.reptile_android.databinding.ItemRvSearchVideoBinding
import cn.edu.swu.reptile_android.model.entity.Video
import cn.edu.swu.reptile_android.ui.base.BaseAdapter
import cn.edu.swu.reptile_android.base.BaseApplication
import cn.edu.swu.reptile_android.ui.base.BindingAdapter
import cn.edu.swu.reptile_android.viewmodel.VideoViewModel

class VideoSearchActivity : AppCompatActivity() {

    val that = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_search)

        val etSearch: EditText = findViewById(R.id.et_search)
        val searchBtn: ImageView = findViewById(R.id.iv_search)
        val rv: RecyclerView = findViewById(R.id.rv)

        val vm = ViewModelProvider(this).get(VideoViewModel::class.java)

        //联想搜索
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                vm.loadSearchData(s.toString())

                val observer = Observer<BaseResponse<List<Video>>> {
                    if (it != null) {
                        if(it.code != 200){
                            Toast.makeText(that, "code: ${it.code}, msg: ${it.msg}", Toast.LENGTH_LONG).show()
                        }else{
                            val app = application as BaseApplication
                            app.detailList = it.data
                            val adapter = BindingAdapter(R.layout.item_rv_search_video, it.data) {
                                    view, video ->
                                val binding: ItemRvSearchVideoBinding? = DataBindingUtil.getBinding(view)
                                if (binding != null) {
                                    binding.video = video
                                    binding.executePendingBindings()
                                }
                            }
                            //点击item
                            adapter.setOnItemClickListener(object : BaseAdapter.OnItemClickListener {
                                override fun onItemClick(position: Int) {
                                    app.detailList = it.data
                                    val intent = Intent(that, VideoDetailActivity::class.java)
                                    intent.putExtra("position", 0)
                                    startActivity(intent)
                                }
                            })

                            rv.adapter = adapter
                            rv.layoutManager = LinearLayoutManager(that)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
                vm.searchData.observe(that, observer)
            }

        })

        //跳转详情
        searchBtn.setOnClickListener {
            val intent = Intent(that, VideoDetailActivity::class.java)
            intent.putExtra("position", 0)
            startActivity(intent)
        }
    }
}