package com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.home_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.snapp.khabar.R
import com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.home_fragment.adapters.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: Fragment() {

    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home,container,false)
        val fragmentManager = activity?.supportFragmentManager
        if(fragmentManager!=null){
            viewPagerAdapter = ViewPagerAdapter(fragmentManager,lifecycle)
        }
        val viewpager = view.findViewById<ViewPager2>(R.id.viewPager)
        viewpager.adapter = viewPagerAdapter
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        TabLayoutMediator(tabLayout,viewpager){tab , position ->
            tab.text = when(position){
                0 -> "All news"
                1 -> "Business"
                2 -> "Politics"
                3 -> "Tech"
                4 -> "Science"
                5 -> "Jobs"
                else -> "Sports"
            }

        }.attach()

        return view
    }


}