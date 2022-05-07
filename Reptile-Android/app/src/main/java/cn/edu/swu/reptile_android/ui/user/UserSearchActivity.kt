package cn.edu.swu.reptile_android.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.swu.reptile_android.R
import cn.edu.swu.reptile_android.base.BaseResponse
import cn.edu.swu.reptile_android.databinding.ItemRvSearchUserBinding
import cn.edu.swu.reptile_android.model.entity.User
import cn.edu.swu.reptile_android.ui.base.BaseActivity
import cn.edu.swu.reptile_android.ui.base.BaseAdapter
import cn.edu.swu.reptile_android.ui.base.BindingAdapter
import cn.edu.swu.reptile_android.ui.main.DetailActivity
import cn.edu.swu.reptile_android.utils.DataUtil
import cn.edu.swu.reptile_android.viewmodel.UserViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class UserSearchActivity : BaseActivity() {
    val that = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_search)

        val etSearch: EditText = findViewById(R.id.et_search)
        val searchBtn: ImageView = findViewById(R.id.iv_search)
        val rv: RecyclerView = findViewById(R.id.rv)

        val vm = UserViewModel()

        //联想搜索
        etSearch.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                vm.loadSearchData(s.toString())

                val observer = Observer<BaseResponse<List<User>>> {
                    if (it != null) {
                        if(it.code != 200){
                            Toast.makeText(that, "code: ${it.code}, msg: ${it.msg}", Toast.LENGTH_LONG).show()
                        }else{
                            val adapter = BindingAdapter(R.layout.item_rv_search_user, it.data) {
                                    view, user ->
                                val binding: ItemRvSearchUserBinding? = DataBindingUtil.getBinding(view)
                                if (binding != null) {
                                    binding.user = user
                                    binding.executePendingBindings()
                                }
                                //头像
                                val options = RequestOptions.bitmapTransform(CircleCrop())
                                Glide.with(view)
                                    .load(user.headImg)
                                    .apply(options)
                                    .into(view.findViewById(R.id.head_img))
                            }
                            //点击item
                            adapter.setOnItemClickListener(object : BaseAdapter.OnItemClickListener {
                                override fun onItemClick(position: Int) {
                                    val user = it.data[position]
                                    val intent = Intent(that, DetailActivity::class.java)
                                    intent.putExtra("dest", "user")
                                    intent.putExtra("key", "id")
                                    intent.putExtra("value", user.id)
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
            val nickname = etSearch.text.toString()
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("dest", "user")
            intent.putExtra("key", "nickname")
            intent.putExtra("value", nickname)
            startActivity(intent)
        }

    }
}