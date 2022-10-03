package com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.home_fragment.fragments.science

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.snapp.khabar.R
import com.snapp.khabar.databinding.FragmentScienceLayoutBinding
import com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.BaseFragment

class ScienceFragment : BaseFragment(1) {

    private var _binding: FragmentScienceLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_science_layout, container, false)
        setUpNewsRecyclerView()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        observeNewsPages(
            newsViewModel.science,
            binding.shimmerScienceLayout,
            binding.contentLayout
        )
    }

    private fun setUpNewsRecyclerView() {
        binding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}