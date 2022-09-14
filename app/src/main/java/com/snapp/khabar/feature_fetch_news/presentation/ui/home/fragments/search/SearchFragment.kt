package com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.search

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.snapp.khabar.R
import com.snapp.khabar.feature_fetch_news.domain.model.ArticleModel
import com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.BaseFragment


class SearchFragment:BaseFragment(2) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val backBtn = view.findViewById<ImageButton>(R.id.btnBack)
        backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        setupResultsRecyclerView(view)

        setupSearchView(view)

        return view
    }

    private fun setupSearchView(view: View) {
        val searchView = view.findViewById<EditText>(R.id.etSearch)

        searchView.doOnTextChanged { text, start, before, count ->
            Handler(Looper.getMainLooper()).postDelayed({
                newsAdapter.submitData(getResultsFound(text.toString()))
            },700)
        }

    }

    private fun getResultsFound(query: String?): List<ArticleModel>{
        if (query != null){
            val numberOfCharsToCompare = query.length
            return emptyList()
        }
        return emptyList()
    }

    private fun setupResultsRecyclerView(view: View) {
        val rvNews = view.findViewById<RecyclerView>(R.id.rvNews)
        rvNews.adapter = newsAdapter
        rvNews.layoutManager = LinearLayoutManager(context)
    }

}