package com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.home_fragment.fragments.jobs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.snapp.khabar.R
import com.snapp.khabar.databinding.BusinessFragmentLayoutBinding
import com.snapp.khabar.databinding.FragmentJobsLayoutBinding
import com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.BaseFragment

class JobsFragment: BaseFragment(1) {

    private var _binding: FragmentJobsLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_jobs_layout, container, false)
        setupNewsRecyclerView()
        return binding.root

    }

    override fun onResume() {
        super.onResume()
        observeNews(
            homeViewModel.businessNewsLiveData,
            binding.shimmerJobsLayout,
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

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_jobs_layout,container,false)
//        setUpNewsRecyclerView(view)
//
//
//        return view
//    }
//    private fun setUpNewsRecyclerView(view: View){
//        val recyclerView = view.findViewById<RecyclerView>(R.id.rvNews)
//        recyclerView.adapter = newsAdapter
//        recyclerView.layoutManager = LinearLayoutManager(context)
//    }
//    override fun onResume() {
//        super.onResume()
//        observeNews(homeViewModel.scienceNewsLiveData)
//    }
//}