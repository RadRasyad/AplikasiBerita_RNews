package com.example.rnews.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.rnews.ui.home.HomeFragment
import com.example.rnews.ui.home.categoryfragment.business.BusinessFragment
import com.example.rnews.ui.home.categoryfragment.entertainment.EntertainmentFragment
import com.example.rnews.ui.home.categoryfragment.health.HealthFragment
import com.example.rnews.ui.home.categoryfragment.news.NewsFragment
import com.example.rnews.ui.home.categoryfragment.sport.SportFragment
import com.example.rnews.ui.home.categoryfragment.technology.TechnologyFragment

class SectionPagerAdapter(fragment: HomeFragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 6

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = NewsFragment()
            1 -> fragment = TechnologyFragment()
            2 -> fragment = BusinessFragment()
            3 -> fragment = EntertainmentFragment()
            4 -> fragment = HealthFragment()
            5 -> fragment = SportFragment()
        }
        return fragment as Fragment
    }

}