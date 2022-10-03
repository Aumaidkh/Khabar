package com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.home_fragment.fragments.business

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.snapp.khabar.R
import com.snapp.khabar.databinding.BusinessFragmentLayoutBinding
import com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.BaseFragment

private const val TAG = "BusinessFragment"

class BusinessFragment : BaseFragment(1) {

    private var _binding: BusinessFragmentLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.business_fragment_layout, container, false)
        setupNewsRecyclerView()
        return binding.root

    }

    override fun onResume() {
        super.onResume()
        observeNewsPages(
            newsViewModel.business,
            binding.shimmerBusinessLayout,
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