package cn.edu.swu.reptile_android.ui.main.adapter

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.edu.swu.reptile_android.R
import cn.edu.swu.reptile_android.model.entity.HomeFun
import com.bumptech.glide.Glide

class FunAdapter(private val context: Context, private val data: List<HomeFun>)
    : RecyclerView.Adapter<FunAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.iv_icon)
        val title: TextView = itemView.findViewById(R.id.tv_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_rv_home_fun, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load(data[position].img)
            .into(holder.img)

        holder.title.text = data[position].title
    }

    override fun getItemCount(): Int {
        return data.size
    }


}