package cn.edu.swu.reptile_android.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import cn.edu.swu.reptile_android.R
import cn.edu.swu.reptile_android.databinding.ItemRvRankUserBinding

class BindingAdapter<T>(
    private val layoutResourceId: Int,
    private val bindingData: List<T>,
    private val init: (View, T) -> Unit
) : BaseAdapter<T>(layoutResourceId, bindingData, init) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater,
            layoutResourceId, parent, false)
        return ViewHolder(binding.root, init)
    }

}