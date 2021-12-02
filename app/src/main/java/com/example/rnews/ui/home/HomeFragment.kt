package com.example.rnews.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.rnews.R
import com.example.rnews.databinding.FragmentHomeBinding
import com.example.rnews.ui.adapter.SectionPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {

        private val TAB_TITLES = intArrayOf(
            R.string.news,
            R.string.technology,
            R.string.business,
            R.string.entertainment,
            R.string.health,
            R.string.sport
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initPagerAdapter()


        return root
    }

    private fun initPagerAdapter() {
        val sectionPagerAdapter =SectionPagerAdapter(this)
        val viewPager : ViewPager2 = binding.viewpager2
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabCategory
        TabLayoutMediator(tabs, viewPager){ tabs, position -> tabs.text = resources.getString(
            TAB_TITLES[position])}.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}