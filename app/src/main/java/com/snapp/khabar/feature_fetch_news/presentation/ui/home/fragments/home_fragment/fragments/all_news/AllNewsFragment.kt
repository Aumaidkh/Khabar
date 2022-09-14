package com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.home_fragment.fragments.all_news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.snapp.khabar.R
import com.snapp.khabar.databinding.FragmentAllNewsBinding
import com.snapp.khabar.feature_fetch_news.domain.util.Result
import com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.BaseFragment
import com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.home_fragment.fragments.all_news.adapters.HeadlineAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllNewsFragment : BaseFragment(1) {

    lateinit var headlineAdapter: HeadlineAdapter

    private var _binding: FragmentAllNewsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_news, container, false)

        setupHeadlinesRecyclerView()

        setupNewsRecyclerView()


        return binding.root

    }

    override fun onResume() {
        super.onResume()
        observeNews(
            homeViewModel.allNewsLiveData,
            binding.shimmerLayout,
            binding.contentLayout
        )
        observeHeadlines()
    }


    private fun observeHeadlines() {
        homeViewModel.headlines.observe(viewLifecycleOwner) { result ->
            when (result) {

                is Result.Loading -> {
                    Log.d(TAG, "Loading: ")

                }
                is Result.Error -> {
                    Log.d(TAG, "Error ${result.message}")

                }
                is Result.Success -> {
                    Log.d(TAG, "Data ${result.data}")
                    val data = result.data
                    if (data != null) {
                        headlineAdapter.submitData(data)
                    }
                }
            }

        }
    }


    private fun setupHeadlinesRecyclerView() {
        headlineAdapter = HeadlineAdapter()

        binding.rvHeadLines.apply {
            adapter = headlineAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

    }

    private fun setupNewsRecyclerView() {

        binding.rvNews.apply {
            isNestedScrollingEnabled = false
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(context)
        }

    }


}

private const val TAG = "AllNewsFragment"
