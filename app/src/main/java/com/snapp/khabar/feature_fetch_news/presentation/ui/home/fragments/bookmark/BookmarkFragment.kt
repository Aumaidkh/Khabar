package com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.bookmark

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.snapp.khabar.R
import com.snapp.khabar.feature_fetch_news.domain.mappers.toNewsEntity
import com.snapp.khabar.feature_fetch_news.presentation.ui.home.HomeViewModel
import com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.BaseFragment
import com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.home_fragment.fragments.all_news.adapters.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "BookmarkFragment"
@AndroidEntryPoint
class BookmarkFragment: Fragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var rootLayout: ConstraintLayout
    private lateinit var newsAdapter: NewsAdapter

    private val itemTouchHelper = object: ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val itemToBeDeleted = newsAdapter.newsList[viewHolder.adapterPosition]
            Log.d(TAG, "Item to be deleted: $itemToBeDeleted")
            homeViewModel.deleteNewsEntity(itemToBeDeleted.toNewsEntity())
            newsAdapter.notifyDataSetChanged()
            Snackbar.make(rootLayout,"Item removed",Snackbar.LENGTH_SHORT).show()

        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bookmark,container,false)

        newsAdapter = NewsAdapter(3){
            homeViewModel.deleteNewsEntity(it.toNewsEntity())
            newsAdapter.notifyDataSetChanged()
        }

        val btnBack = view.findViewById<ImageButton>(R.id.btnBack)
        rootLayout = view.findViewById(R.id.rootLayout)
        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        setupNewsRecyclerView(view)

       // setupObservers()

        return view
    }

    override fun onResume() {
        super.onResume()
        setupObservers()
    }

    private fun setupObservers() {
        homeViewModel.savedNews.observe(viewLifecycleOwner) { newsList ->
            Log.d(TAG, "setupObservers: ${newsList.size}")
            newsAdapter.submitData(newsList)
        }
    }

    private fun setupNewsRecyclerView(view: View){
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvNews)
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView)
        recyclerView.adapter = newsAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

    }
}