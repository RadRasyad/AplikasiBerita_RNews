package com.example.rnews.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionBar()

        article = intent.getParcelableExtra(DETAIL_NEWS)

        if (article!=null) {
            binding.apply {
                tvDTitle.text = article?.title
                tvDAuthor.text = article?.author
                tvDContent.text = article?.content
                Glide.with(applicationContext)
                    .load(article?.urlToImage)
                    .placeholder(android.R.drawable.progress_horizontal)
                    .error(android.R.drawable.stat_notify_error)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivDNews)
            }
        }
    }

    private fun initActionBar() {
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_line)
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