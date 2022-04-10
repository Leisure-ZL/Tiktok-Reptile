package cn.edu.swu.reptile_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import cn.edu.swu.reptile_android.ui.MainFragment
import cn.edu.swu.reptile_android.ui.MyFragment
import cn.edu.swu.reptile_android.ui.UserFragment
import cn.edu.swu.reptile_android.ui.VideoFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

    }

    private fun init() {

        val navView = findViewById<BottomNavigationView>(R.id.btm_nav_view)

        val navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_main, R.id.nav_user, R.id.nav_video, R.id.nav_my))

        setupActionBarWithNavController(navController, appBarConfiguration)

        navView.setupWithNavController(navController)




    }
}