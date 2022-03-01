package com.example.rnews.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rnews.databinding.FragmentSearchBinding
import com.example.rnews.model.ArticleResponse
import com.example.rnews.ui.adapter.NewsAdapter
import com.example.rnews.ui.detail.DetailActivity

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emptyState(true)
        searchNews()

        binding.apply {
            rvNews.layoutManager = LinearLayoutManager(context)
            rvNews.setHasFixedSize(true)
        }

        adapter = NewsAdapter()

        searchViewModel = ViewModelProvider(requireActivity())[SearchViewModel::class.java]
        searchViewModel.getNews().observe(requireActivity(),{
            if (it!=null) {
                emptyState(false)
                adapter.setList(it)
                showProgress(false)
                binding.rvNews.adapter = adapter
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

    private fun searchNews() {
        binding.apply {
            svNews.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        if (it.isNotEmpty()) {
                            searchViewModel.setNews(query)
                            showProgress(true)
                        }
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean = true
            })
        }
    }

    private fun showProgress(state: Boolean) {
        if (state) {
            binding.lProgressbar.visibility = View.VISIBLE
        }
        else {
            binding.lProgressbar.visibility = View.GONE
        }

    }

    private fun emptyState(state: Boolean) {
        if (state) {
            binding.emptyState.root.visibility = View.VISIBLE
        }
        else {
            binding.emptyState.root.visibility = View.GONE
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}