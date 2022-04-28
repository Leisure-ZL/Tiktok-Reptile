package cn.edu.swu.reptile_android.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

open class BaseAdapter<T>(
    private val layoutResourceId: Int,
    val data: List<T>,
    private val init: (View, T) -> Unit
) :
    RecyclerView.Adapter<BaseAdapter.ViewHolder<T>>() {

    private var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> {
        val view = LayoutInflater.from(parent.context).inflate(layoutResourceId, parent, false)
        return ViewHolder(view, init)
    }

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(position)
        }
        holder.bindForecast(data[position])
    }

    override fun getItemCount() = data.size

    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class ViewHolder<in T>(view: View, val init: (View, T) -> Unit) :
        RecyclerView.ViewHolder(view) {
        fun bindForecast(item: T) {
            with(item) {
                init(itemView, item)
            }
        }
    }
}