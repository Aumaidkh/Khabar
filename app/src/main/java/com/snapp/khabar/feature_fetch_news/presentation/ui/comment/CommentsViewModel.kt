package com.snapp.khabar.feature_fetch_news.presentation.ui.comment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.snapp.khabar.feature_fetch_news.data.repository.SubmitCommentUseCase
import com.snapp.khabar.feature_fetch_news.domain.model.CommentModel
import com.snapp.khabar.feature_fetch_news.domain.use_cases.FetchAllCommentsForNews
import com.snapp.khabar.feature_fetch_news.domain.use_cases.ValidateCommentUseCase
import com.snapp.khabar.feature_fetch_news.domain.util.Result
import com.snapp.khabar.feature_fetch_news.presentation.ui.comment.adapters.CommentsScreenEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val submitCommentUseCase: SubmitCommentUseCase,
    private val fetchAllCommentsForNews: FetchAllCommentsForNews,
    private val validateCommentUseCase: ValidateCommentUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CommentState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<CommentsEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun onEvent(event: CommentsScreenEvents) {
        when (event) {
            is CommentsScreenEvents.SubmitCommentEvent -> {
                submitComment(
                    userId = event.userId,
                    userName = event.userName,
                    comment = event.comment,
                    newsId = event.newsId,
                    imageUrl = event.userImageUrl
                )
            }

            is CommentsScreenEvents.OnCommentChange -> {
                _state.update {
                    it.copy(message = event.comment)
                }
            }

            is CommentsScreenEvents.FetchComments -> {
                Log.d(TAG, "onEvent: Fetching Comments")
                fetchAllCommentsForNews(newsId = event.newsId)
            }


        }
    }

    private val TAG = "CommentsViewModel"

    private fun fetchAllCommentsForNews(newsId: String) {
        fetchAllCommentsForNews.invoke(newsId).onEach { result ->
             when(result){
                 is Result.Loading -> {
                     Log.d(TAG, "Loading")
                     _state.update {
                         it.copy(isLoading = true)
                     }
                 }

                 is Result.Success -> {
                     Log.d(TAG, "Success Data -> ${result.data}")
                     _state.update {
                         it.copy(isLoading = false, data = result.data!!)
                     }
                 }

                 is Result.Error -> {
                     Log.d(TAG, "Error")
                     _state.update {
                         it.copy(isLoading = false, data = emptyList(), message = result.message)
                     }
                 }
             }
        }.launchIn(viewModelScope)
    }


    private fun submitComment(
        userId: String,
        userName: String,
        comment: String,
        newsId: String,
        imageUrl: String
    ) {

        viewModelScope.launch {
            submitCommentUseCase.invoke(
                userName = userName,
                userId = userId,
                comment = comment,
                newsId = newsId,
                imageUrl = imageUrl
            ).onEach { result ->
                when (result) {
                    is Result.Success -> {
                        _state.update {
                            it.copy(isLoading = false, message = null)
                        }
                        _eventFlow.emit(CommentsEvent.SuccessEvent)
                    }

                    is Result.Loading -> {
                        _state.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Result.Error -> {
                        _state.update {
                            it.copy(isLoading = false, message = result.message)
                        }
                        _eventFlow.emit(CommentsEvent.ShowSnackBarEvent(result.message.toString()))
                    }
                }
            }.launchIn(this)

        }

    }

}

sealed class CommentSubmissionState {
    object Success : CommentSubmissionState()
    object Loading : CommentSubmissionState()
    data class Failure(
        val message: String
    ) : CommentSubmissionState()
}

sealed class CommentsEvent {
    object SuccessEvent : CommentsEvent()
    data class ShowSnackBarEvent(val message: String) : CommentsEvent()
}

data class CommentState(
    val isLoading: Boolean = true,
    val data: List<CommentModel> = emptyList(),
    val message: String? = null
)


