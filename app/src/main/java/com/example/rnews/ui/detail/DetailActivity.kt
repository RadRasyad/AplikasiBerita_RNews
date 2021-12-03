package com.example.rnews.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.rnews.R
import com.example.rnews.databinding.ActivityDetailBinding
import com.example.rnews.model.ArticleResponse
import com.example.rnews.model.NewsResponse

private lateinit var binding: ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    companion object {
        const val DETAIL_NEWS = "DETAIL_NEWS"
    }

    private var article: ArticleResponse? = null
    private lateinit var webView: WebView

    private var newsTitle: String? = null
    private var newsUrl: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        article = intent.getParcelableExtra(DETAIL_NEWS)

        if (article!=null) {
            newsTitle = article?.title
            newsUrl = article?.url
        }

        initActionBar()
        initWebView()

    }

    private fun initActionBar() {
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        //set back button
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_line)

        //set title dan subtitle
        binding.apply {
            tvTitle.text = newsTitle
            tvSubTitle.text = newsUrl
        }
    }

    private fun initWebView() {
        webView = binding.webView
        webView.settings.javaScriptEnabled = true
        newsUrl?.let { webView.loadUrl(it) }

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, newUrl: String): Boolean {
                view.loadUrl(newUrl)
                binding.progressHorizontal.visibility = View.VISIBLE
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                binding.progressHorizontal.visibility = View.GONE
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }


}