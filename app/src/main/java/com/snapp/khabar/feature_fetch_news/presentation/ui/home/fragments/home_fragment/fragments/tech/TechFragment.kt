package com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.home_fragment.fragments.tech

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.snapp.khabar.R
import com.snapp.khabar.databinding.BusinessFragmentLayoutBinding
import com.snapp.khabar.databinding.FragmentTechLayoutBinding
import com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.BaseFragment

class TechFragment: BaseFragment(1) {

    private var _binding: FragmentTechLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_tech_layout, container, false)
        setupNewsRecyclerView()
        return binding.root

    }

    override fun onResume() {
        super.onResume()
        observeNews(
            homeViewModel.techNewsLiveData,
            binding.shimmerTechLayout,
            binding.contentLayout
        )
    }

    private fun setupNewsRecyclerView() {
        binding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(context)

        }

    }
}