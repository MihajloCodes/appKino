package com.mozzart.grckikino.talon.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.google.gson.Gson
import com.mozzart.grckikino.R
import com.mozzart.grckikino.databinding.ActivityLiveGameBinding
import com.mozzart.grckikino.global.BaseActivity
import com.mozzart.grckikino.main.data.Kino

class LiveGameActivity : BaseActivity() {

    private lateinit var binding: ActivityLiveGameBinding

    companion object {
        fun newInit(activity: Activity) {
            val intent = Intent(activity, LiveGameActivity::class.java)
            activity.startActivity(intent)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLiveGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setStatusBarColor(R.color.dark_background)

        val url = "https://ds.opap.gr/web_kino/kinoIframe.html?link=https://ds.opap.gr/web_kino/kino/html/Internet_PRODUCTION/KinoDraw_201910.html&resolution=847x500"

        binding.apply {
            webView.settings.javaScriptEnabled = true
            webView.webViewClient = WebViewClient()
            webView.loadUrl(url)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        binding.apply {
            webView.stopLoading()
        }
    }

}