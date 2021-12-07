package com.example.rnews.ui.home.categoryfragment.health

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rnews.BuildConfig
import com.example.rnews.api.ApiConfig
import com.example.rnews.databinding.FragmentHealthBinding
import com.example.rnews.model.ArticleResponse
import com.example.rnews.model.NewsResponse
import com.example.rnews.ui.adapter.NewsAdapter
import com.example.rnews.ui.detail.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HealthFragment : Fragment() {
    private var _binding: FragmentHealthBinding? = null
    private val binding get() = _binding

    private lateinit var healthViewModel: HealthViewModel
    private lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FrameLayout? {
        // Inflate the layout for this fragment
        _binding = FragmentHealthBinding.inflate(inflater, container,false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            rvNews.layoutManager = LinearLayoutManager(context)
            rvNews.setHasFixedSize(true)
        }

        adapter = NewsAdapter()

        healthViewModel = ViewModelProvider(requireActivity())[HealthViewModel::class.java]
        healthViewModel.setNews()
        healthViewModel.getNews().observe(requireActivity(), {
            if (it!=null) {
                adapter.setList(it)
                binding?.rvNews?.adapter = adapter
                adapter.notifyDataSetChanged()
                //onclick item
                adapter.setOnItemClickCallback(object : NewsAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: ArticleResponse) {
                        Intent(context, DetailActivity::class.java).also {
                            it.putExtra(DetailActivity.DETAIL_NEWS, data)
                            startActivity(it)
                        }
                    }
                })
            }
        })

    }

}