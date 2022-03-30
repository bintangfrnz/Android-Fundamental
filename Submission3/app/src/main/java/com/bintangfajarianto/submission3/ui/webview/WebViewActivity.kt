package com.bintangfajarianto.submission3.ui.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.bintangfajarianto.submission3.R
import com.bintangfajarianto.submission3.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.myToolbar)
        supportActionBar?.apply {
            title = "Web View"
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            setDisplayHomeAsUpEnabled(true)
        }

        val url = intent.getStringExtra(URL) as String

        // Set up WebView
        binding.webView.apply {
            loadUrl(url)
            webViewClient = WebViewClient()
        }
    }

    companion object {
        const val URL = "url"
    }
}