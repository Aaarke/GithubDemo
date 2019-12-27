package com.example.github.webView

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.ProgressBar
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.github.R
import kotlinx.android.synthetic.main.activity_repo_detail.*

import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.activity_web_view.toolbar
import kotlinx.android.synthetic.main.content_web_view.*

class WebViewActivity : AppCompatActivity() {
    private var dialog: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        setSupportActionBar(toolbar)
        dialog = ProgressBar(this, null, android.R.attr.progressBarStyleLarge)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = getString(R.string.repo)
        loadWebView(intent.getStringExtra("url"))
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadWebView(url: String) {
        webView.webViewClient = MyWebViewClient()
        webView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        webView.webChromeClient = WebChromeClient()
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                // Keep WebView hidden when the url is loading
                webView.visibility = View.INVISIBLE
                // Make ProgressBar visible
                progressBar.visibility = View.VISIBLE
                progressBar.progress = 0
                progressBar.incrementProgressBy(progress)

                if (progress == 100 && progressBar.visibility == View.VISIBLE) {
                    // Make ProgressBar invisible and WebView visible
                    progressBar.visibility = View.INVISIBLE
                    webView.visibility = View.VISIBLE
                }
            }
        }
        webView.settings.loadsImagesAutomatically = true
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.isVerticalScrollBarEnabled = true
        webView.isHorizontalScrollBarEnabled = true
        webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webView.loadUrl(url)
    }

    private inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.loadUrl(request.url.toString())
                Log.e("URL", request.url.toString() + "")

            }
            return true
        }


        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            Log.e("URL", url + "")
            return true
        }


    }


}
