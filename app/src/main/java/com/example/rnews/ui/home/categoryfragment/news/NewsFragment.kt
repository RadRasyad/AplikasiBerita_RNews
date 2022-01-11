package com.example.rnews.ui.home.categoryfragment.news

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rnews.databinding.FragmentNewsBinding
import com.example.rnews.model.ArticleResponse
import com.example.rnews.ui.adapter.NewsAdapter
import com.example.rnews.ui.detail.DetailActivity


class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding

    private lateinit var newsViewModel: NewsViewModel
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
        adapter = NewsAdapter()

        newsViewModel = ViewModelProvider(requireActivity())[NewsViewModel::class.java]
        newsViewModel.setNews()
        newsViewModel.getNews().observe(requireActivity(), {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}