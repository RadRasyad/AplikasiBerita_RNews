package com.example.rnews.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.rnews.databinding.NewsRowBinding
import com.example.rnews.model.ArticleResponse
import com.example.rnews.ui.detail.DetailActivity

class BeritaAdapter : RecyclerView.Adapter<BeritaAdapter.NewsViewHolder>() {

    private var listNews = ArrayList<ArticleResponse>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onitemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onitemClickCallback
    }

    fun setList(news: ArrayList<ArticleResponse>) {
        listNews.clear()
        listNews.addAll(news)
        notifyDataSetChanged()
    }

    inner class NewsViewHolder(private val binding: NewsRowBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: ArticleResponse) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(article.urlToImage)
                    .placeholder(android.R.drawable.progress_horizontal)
                    .error(android.R.drawable.stat_notify_error)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivNews)
                tvTitle.text = article.title
                tvSource.text = article.modelSource?.name
                binding.root.setOnClickListener {
                    onItemClickCallback?.onItemClicked(article)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = NewsRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(listNews[position])
    }

    override fun getItemCount(): Int = listNews.size

    interface OnItemClickCallback {
        fun onItemClicked(data: ArticleResponse)
    }

}