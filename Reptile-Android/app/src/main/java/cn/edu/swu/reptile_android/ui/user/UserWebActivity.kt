package cn.edu.swu.reptile_android.ui.user

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import cn.edu.swu.reptile_android.R

class UserWebActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_web)

        val webView: WebView = findViewById(R.id.wv)
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true

        val url = intent.getStringExtra("url")

        if (url != null) {
            webView.loadUrl(url)
        }

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                //根据url真正去加载网页的操作
                view.loadUrl(url)
                //在当前WebView中打开网页，而不在浏览器中
                return true
            }
        }

    }
}