package cn.edu.swu.reptile_android.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import cn.edu.swu.reptile_android.R
import cn.edu.swu.reptile_android.databinding.ItemRvRankUserBinding
import cn.edu.swu.reptile_android.model.entity.User
import cn.edu.swu.reptile_android.utils.DataUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class UserRankAdapter(val context: Context, val data: List<User>) : RecyclerView.Adapter<UserRankAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headImg: ImageView = itemView.findViewById(R.id.iv_head)
//        val nickname: TextView = itemView.findViewById(R.id.tv_nickname)
//        val followerCount: TextView = itemView.findViewById(R.id.tv_follower_count)
        val followerIncremental: TextView = itemView.findViewById(R.id.tv_follower_incremental)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
       // val itemView = LayoutInflater.from(context).inflate(R.layout.item_rv_rank_user, parent, false)
        val binding: ItemRvRankUserBinding = DataBindingUtil.inflate(inflater,
            R.layout.item_rv_rank_user, parent, false)

        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding: ItemRvRankUserBinding? = DataBindingUtil.getBinding(holder.itemView)
        val user = data[position]
        if (binding != null) {
            binding.user = user
            binding.executePendingBindings()
        }
        //TODO:真机调试
      //  Glide.with(context).load(data[position].headImg).into(holder.headImg)
        val roundedCorners = RoundedCorners(60)
        val options = RequestOptions.bitmapTransform(roundedCorners)
        Glide.with(context)
            .load(R.drawable.test_head_user)
            .apply(options)
            .into(holder.headImg)
       // holder.nickname.text = user.nickname
       // holder.followerCount.text = user.followerCount
        holder.followerIncremental.text = DataUtil.numToString(user.followerIncremental)

    }

    override fun getItemCount(): Int {
        return data.size
    }

}