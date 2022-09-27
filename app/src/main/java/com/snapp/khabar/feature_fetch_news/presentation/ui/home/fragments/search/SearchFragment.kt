package com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.snapp.khabar.R
import com.snapp.khabar.databinding.FragmentSearchBinding
import com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SearchFragment : BaseFragment(2) {

    /**
     * Binding Vars
     * */
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    /**
     * ViewModels
     * */
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)

        setupClickListeners()

        consumeFlows()

        setupResultsRecyclerView()

        setupSearchView()

        return binding.root
    }

    private fun setupClickListeners() {
        binding.apply {
            /**
             * Back Button
             * */
            btnBack.setOnClickListener {
                viewModel.onEvent(SearchEvents.Actions.ActionBack)
            }

        }
    }

    private fun setupSearchView() {
        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            viewModel.onEvent(SearchEvents.Actions.OnSearchQueryChanged(text.toString()))
        }
    }


    private fun setupResultsRecyclerView() {

        binding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    /**
     * Consuming Flows From ViewModel
     * */
    private fun consumeFlows() {
        /**
         * Collecting Events
         * */
        lifecycleScope.launchWhenStarted {
            viewModel.eventFlow.collect { event ->
                when (event) {
                    is SearchEvents.UiEvents.CloseScreen -> {
                        findNavController().navigateUp()
                    }
                }
            }
        }

        /**
         * Collecting State
         * */
        lifecycleScope.launchWhenStarted {
            viewModel.results.collect { state ->

                if (state.searchResults.isEmpty()) {
                    binding.errorLayout.visibility = View.VISIBLE

                } else {
                    binding.apply {
                        errorLayout.visibility = View.GONE
                        rvNews.visibility = View.VISIBLE
                    }
                    newsAdapter.submitData(state.searchResults)
                }
            }
        }
    }

    /**
     * Freeing up resources
     * */
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}

private const val TAG = "SearchFragment"