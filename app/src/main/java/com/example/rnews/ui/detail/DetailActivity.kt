package com.example.rnews.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProvider
import com.example.rnews.R
import com.example.rnews.databinding.ActivityDetailBinding
import com.example.rnews.model.ArticleResponse
import com.example.rnews.model.DetailResponse
import com.example.rnews.model.NewsResponse

private lateinit var binding: ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

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

            R.id.share_option -> {
                val share = Intent(Intent.ACTION_SEND)
                share.type = "text/plain"
                share.putExtra(Intent.EXTRA_TEXT, newsUrl)
                startActivity(Intent.createChooser(share, "Bagikan ke : "))
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    companion object {
        const val DETAIL_NEWS = "DETAIL_NEWS"
    }

}