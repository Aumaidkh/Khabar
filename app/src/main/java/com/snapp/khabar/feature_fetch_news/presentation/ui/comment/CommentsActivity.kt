package com.snapp.khabar.feature_fetch_news.presentation.ui.comment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.snapp.khabar.R
import com.snapp.khabar.databinding.ActivityCommentsLayoutBinding
import com.snapp.khabar.feature_fetch_news.presentation.ui.comment.adapters.CommentsAdapter
import com.snapp.khabar.feature_fetch_news.presentation.ui.comment.adapters.CommentsScreenEvents
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommentsActivity : AppCompatActivity() {

    /**
     * Binding Vars
     * */
    private var _binding: ActivityCommentsLayoutBinding? = null
    private val binding get() = _binding!!

    /**
     * Other Vars*/
    private lateinit var newsId: String


    private lateinit var commentsAdapter: CommentsAdapter

    /**
     * ViewModels
     * */
    private val commentsViewModel: CommentsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_comments_layout)
        commentsAdapter = CommentsAdapter()

        intent?.getStringExtra("newsId").also {
            if (it != null) {
                newsId = it
                commentsViewModel.onEvent(CommentsScreenEvents.FetchComments(newsId))
            }
        }



        setUpCommentsRecyclerView()


        setUpClicks()
    }

    override fun onResume() {
        super.onResume()
        consumeFlows()
    }

    private fun setUpCommentsRecyclerView() {
        findViewById<RecyclerView>(R.id.commentsRv).apply {
            layoutManager = LinearLayoutManager(this@CommentsActivity)
            adapter = commentsAdapter
        }
    }

    /**
     * Setup clicks
     * */
    private fun setUpClicks() {
        binding.apply {
            /**
             * Back Button*/
            btnBack.setOnClickListener {
                finish()
            }

            /**
             * Submit Comment
             * */
            btnSubmitComment.setOnClickListener {
                commentsViewModel.onEvent(
                    CommentsScreenEvents.SubmitCommentEvent(
                        comment = binding.editText.text.toString().trim(),
                        newsId = newsId
                    )
                )
            }
        }
    }

    /**
     * Consuming all the observables
     * */
    private fun consumeFlows() {
        lifecycleScope.launchWhenStarted {
            /**
             * Observing events
             * */
            commentsViewModel.eventFlow.collect { event ->
                when (event) {
                    is CommentsEvent.ShowSnackBarEvent -> {
                        binding.root.also {
                            Snackbar.make(it, event.message, Snackbar.LENGTH_SHORT).show()
                        }
                    }

                    is CommentsEvent.SuccessEvent -> {
                       // finish()
                        // Clearing the text comments textview
                        binding.editText.setText("")
                        // Refreshing the comments
                        commentsViewModel.onEvent(CommentsScreenEvents.FetchComments(newsId))
                    }
                }
            }

        }

        lifecycleScope.launchWhenStarted {
            /**
             * Observing Comments state
             * */
            commentsViewModel.state.collect { state ->
                if (state.isLoading){
                    // Show Progressbar
                    binding.progressBar.visibility = View.VISIBLE
                } else if (!state.isLoading){
                    commentsAdapter.submitData(state.data)
                    binding.progressBar.visibility = View.GONE
                } else {
                    binding.root.also {
                        Snackbar.make(it,state.message!!,Snackbar.LENGTH_SHORT).show()
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}

private const val TAG = "CommentsActivity"