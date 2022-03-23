package com.bintangfajarianto.submission2.ui.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebViewClient
import com.bintangfajarianto.submission2.R
import com.bintangfajarianto.submission2.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Web View"
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val url = intent.getStringExtra(URL) as String
        val webView = binding.webView
        webView.loadUrl(url)
        webView.webViewClient = WebViewClient()
    }

    companion object {
        const val URL = "url"
    }
}