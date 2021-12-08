package com.example.rnews.ui.home.categoryfragment.news

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rnews.BuildConfig
import com.example.rnews.api.ApiConfig
import com.example.rnews.model.ArticleResponse
import com.example.rnews.model.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {

    val listArticle = MutableLiveData<ArrayList<ArticleResponse>?>()

    private val country = "id"
    private val apiKey = secret

    fun setNews() {
        ApiConfig.apiInstance
            .getHeadlines(country, apiKey)
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    if (response.isSuccessful) {
                        listArticle.postValue(response.body()?.modelArticle)
                    }

                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    Log.d("Failed to get data", t.message!!)
                }
            })
    }

    fun getNews(): MutableLiveData<ArrayList<ArticleResponse>?> {
        return listArticle
    }

    companion object {
        const val secret = BuildConfig.KEY
    }
}