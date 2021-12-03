package com.example.rnews.ui.home.categoryfragment.technology

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
import com.example.rnews.databinding.FragmentTechnologyBinding
import com.example.rnews.model.ArticleResponse
import com.example.rnews.model.NewsResponse
import com.example.rnews.ui.adapter.NewsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TechnologyFragment : Fragment() {
    private var _binding: FragmentTechnologyBinding? = null
    private val binding get() = _binding

    var listArticle: MutableList<ArticleResponse> = ArrayList()
    private lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FrameLayout? {
        // Inflate the layout for this fragment
        _binding = FragmentTechnologyBinding.inflate(inflater, container,false)
        return _binding?.root
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

        //get country/
        val country = "id"
        val category = "technology"
        val apiKey = secret

        //set api
        val apiInterface = ApiConfig.apiInstance
        val call = apiInterface.getNewsCategory(country, category, apiKey)
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

    companion object {
        //get api key
        const val secret = BuildConfig.KEY
    }
}