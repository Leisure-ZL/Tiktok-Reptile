package cn.edu.swu.reptile_android.ui.base

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.swu.reptile_android.R

class DropdownMenu (
    val context: Context?
) {

    lateinit var maskView: View
    lateinit var tabView: LinearLayout
    lateinit var tabTitle: TextView
    lateinit var tabIcon: ImageView

    lateinit var data: List<String>

    private var onItemSelectListener: OnItemSelectListener? = null

    public fun init(view: View,data: List<String>) {

        this.data = data
        tabView = view.findViewById(R.id.ly_dropdown_tab)
        tabTitle = view.findViewById(R.id.tv_dropdown_title)
        tabIcon = view.findViewById(R.id.iv_dropdown_icon)
        maskView = view.findViewById(R.id.mask_view)

        //默认显示item
        tabTitle.text = data[0]

        tabView.setOnClickListener {
            //角标变化
            tabIcon.setImageResource(R.drawable.ic_down)
            //遮罩层动画
            maskView.startAnimation(
                AnimationUtils.loadAnimation(
                    context,
                    R.anim.view_mask_enter_anim
                )
            )
            //弹出popWin
            showPopupWindow(tabView)
        }

    }

    public fun setOnItemSelectListener(onItemSelectListener: OnItemSelectListener){
        this.onItemSelectListener = onItemSelectListener
    }

    interface OnItemSelectListener{
        fun onItemSelect(position: Int)
        fun onDismiss()
    }

    private fun showPopupWindow(tabView: View) {
        val contentView: View =
            LayoutInflater.from(context).inflate(R.layout.popup_dropdown_menu, null)
        val popWindow = PopupWindow(
            contentView, ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT, true
        )
        popWindow.contentView = contentView

        //RV
        val dropdownRv: RecyclerView = contentView.findViewById(R.id.rv)
        dropdownRv.layoutManager = LinearLayoutManager(contentView.context)
        val adapter = BaseAdapter(R.layout.item_rv_dropdown, data) { view, s ->
            view.findViewById<TextView>(R.id.tv_item_title).text = s
            if (s == tabTitle.text) { //当前选中的item
                view.findViewById<TextView>(R.id.tv_item_title).setTextColor(Color.BLACK)
                view.findViewById<ImageView>(R.id.iv_item_icon).visibility = View.VISIBLE
            }
        }
        //select item
        adapter.setOnItemClickListener(object : BaseAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                popWindow.dismiss()
                tabTitle.text = data[position]
                //加载数据
                //暴露给调用者自定义选项逻辑
                onItemSelectListener?.onItemSelect(position)

            }
        })
        dropdownRv.adapter = adapter

        //弹出动画
        popWindow.animationStyle = R.style.popwin_anim

        //遮罩效果
        maskView.visibility = View.VISIBLE
        popWindow.setOnDismissListener {
            maskView.visibility = View.GONE
            tabIcon.setImageResource(R.drawable.ic_up)
            onItemSelectListener?.onDismiss()
        }

        //弹出窗口
        popWindow.showAsDropDown(tabView)
    }


}