package com.example.rnews.ui.home.categoryfragment.news

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rnews.BuildConfig
import com.example.rnews.api.ApiConfig
import com.example.rnews.databinding.FragmentNewsBinding
import com.example.rnews.model.ArticleResponse
import com.example.rnews.model.NewsResponse
import com.example.rnews.ui.adapter.NewsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding

    var listArticle: MutableList<ArticleResponse> = ArrayList()
    private lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FrameLayout? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            rvNews.layoutManager = LinearLayoutManager(context)
            rvNews.setHasFixedSize(true)
        }
        getListNews()

    }

    private fun getListNews() {

        val country = "id"
        val apiKey = secret

        //model(set api)
        val apiInterface = ApiConfig.apiInstance
        val call = apiInterface.getHeadlines(country, apiKey)
        call.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    listArticle = response.body()?.modelArticle as MutableList<ArticleResponse>
                    adapter = context?.let { NewsAdapter(listArticle, it) }!!
                    binding?.rvNews?.adapter = adapter
                    adapter?.notifyDataSetChanged()

                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Log.d("Failed to get data", t.message!!)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val secret = BuildConfig.KEY
    }

}