package cn.edu.swu.reptile_android.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import cn.edu.swu.reptile_android.R
import cn.edu.swu.reptile_android.ui.base.BaseActivity
import cn.edu.swu.reptile_android.ui.user.UserFragment
import cn.edu.swu.reptile_android.ui.video.VideoFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNavAndPager()

    }



    private fun initNavAndPager() {
        val vp2 = findViewById<ViewPager2>(R.id.vp2_main)
        val navView = findViewById<BottomNavigationView>(R.id.btm_nav_view)

        //禁止滑动
        vp2.isUserInputEnabled = false

        navView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_main -> {
                    vp2.currentItem = 0
                    navView.menu.getItem(0).isChecked = true
                }
                R.id.nav_user -> {
                    vp2.currentItem = 1
                    navView.menu.getItem(1).isChecked = true
                }
                R.id.nav_video -> {
                    vp2.currentItem = 2
                    navView.menu.getItem(2).isChecked = true
                }
                else -> {
                    vp2.currentItem = 3
                    navView.menu.getItem(3).isChecked = true
                }
            }
            false
        }

        vp2.registerOnPageChangeCallback(object:ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                navView.menu.getItem(position).isChecked = true
            }
        })

        val fragments = listOf(HomeFragment(), UserFragment(), VideoFragment(), MyFragment())


        vp2.adapter = object: FragmentStateAdapter(this) {
            override fun getItemCount(): Int = fragments.size

            override fun createFragment(position: Int): Fragment = fragments[position]

        }
    }


}


