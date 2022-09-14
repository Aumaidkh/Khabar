package com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.home_fragment.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.home_fragment.fragments.science.ScienceFragment
import com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.home_fragment.fragments.all_news.AllNewsFragment
import com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.home_fragment.fragments.business.BusinessFragment
import com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.home_fragment.fragments.jobs.JobsFragment
import com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.home_fragment.fragments.politics.PoliticsFragment
import com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.home_fragment.fragments.sports.SportsFragment
import com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.home_fragment.fragments.tech.TechFragment

const val NUMBER_OF_TABS = 7
class ViewPagerAdapter(
    private val fragmentManager: FragmentManager,
    private val lifecycle: Lifecycle
):FragmentStateAdapter(fragmentManager , lifecycle) {
    override fun getItemCount(): Int {
       return NUMBER_OF_TABS
    }

    override fun createFragment(position: Int): Fragment {
       return when(position){
           0 -> { AllNewsFragment() }
           1 -> {
               BusinessFragment()
           }
           2 -> {
               PoliticsFragment()
           }
           3 -> {
               TechFragment()
           }
           4 -> {
               ScienceFragment()
           }
           5 -> {
               JobsFragment()
           }
           else -> {
               SportsFragment()
           }

       }
    }
}