package cn.edu.swu.reptile_android.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import cn.edu.swu.reptile_android.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val vp2 = findViewById<ViewPager2>(R.id.vp2_main)
        val navView = findViewById<BottomNavigationView>(R.id.btm_nav_view)

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

        initPager()

    }

    private fun initPager() {

        val fragments = listOf(HomeFragment(), UserFragment(), VideoFragment(), MyFragment())

        //TODO:adapter

    }

}