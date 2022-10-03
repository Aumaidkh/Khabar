package com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments

import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.paging.map
import com.facebook.shimmer.ShimmerFrameLayout
import com.snapp.khabar.feature_fetch_news.domain.mappers.toNewsEntity
import com.snapp.khabar.feature_fetch_news.domain.model.ArticleModel
import com.snapp.khabar.feature_fetch_news.domain.util.Result
import com.snapp.khabar.feature_fetch_news.presentation.ui.home.HomeViewModel
import com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.home_fragment.fragments.all_news.NewsViewModel
import com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.home_fragment.fragments.all_news.adapters.NewsAdapter
import com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.home_fragment.fragments.all_news.adapters.NewsPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect

private const val TAG = "BaseFragment"
@AndroidEntryPoint
open class BaseFragment(
    private val adapterType: Int
): Fragment() {



    val homeViewModel: HomeViewModel by viewModels()
    val newsViewModel: NewsViewModel by viewModels()

    /**
     * Making News Adapter a Global variable so that data can be submitted to it
     * from any function
     * */
    var newsAdapter: NewsPagingAdapter = NewsPagingAdapter(adapterType){
        homeViewModel.insertNewsEntity(it.toNewsEntity())
    }

//    fun observeNews(observable: LiveData<Result<List<ArticleModel>>>) {
//        observable.observe(viewLifecycleOwner) { result ->
//            newsAdapter.submitData(result)
////            when (result) {
////
////                is Result.Loading -> {
////                    Log.d(TAG, "Loading: ")
////
////                }
////                is Result.Error -> {
////                    Log.d(TAG, "Error ${result.message}")
////
////                }
////                is Result.Success -> {
////                    Log.d(TAG, "Data ${result.data}")
////                    val data = result.data
////                    if (data != null) {
////                        newsAdapter.submitData(data)
////                    }
////                }
////            }
//
//        }
//    }

//    fun observeNews(observable: LiveData<Result<List<ArticleModel>>>, shimmerFrameLayout: ShimmerFrameLayout, content: ConstraintLayout) {
//        observable.observe(viewLifecycleOwner) { result ->
//
//            when (result) {
//
//                is Result.Loading -> {
//                    Log.d(TAG, "Loading: ")
//                    shimmerFrameLayout.visibility = View.VISIBLE
//                    content.visibility = View.GONE
//                }
//                is Result.Error -> {
//                    Log.d(TAG, "Error ${result.message}")
//                    shimmerFrameLayout.visibility = View.GONE
//                    content.visibility = View.VISIBLE
//                }
//                is Result.Success -> {
//                    shimmerFrameLayout.visibility = View.GONE
//                    content.visibility = View.VISIBLE
//                    Log.d(TAG, "Data ${result.data}")
//                    val data = result.data
//                    if (data != null) {
//                        newsAdapter.submitData(data)
//                    }
//                }
//            }
//
//        }
//    }

    fun observeNewsPages(observable: Flow<PagingData<ArticleModel>?>, shimmerFrameLayout: ShimmerFrameLayout, content: ConstraintLayout){
        lifecycleScope.launchWhenStarted {
            observable.collect {
                shimmerFrameLayout.visibility = View.GONE
                content.visibility = View.VISIBLE
                if (it != null){
                    newsAdapter.submitData(it)
                } else {
                    Log.d(TAG, "Page is Null")
                }

            }
        }
    }




}