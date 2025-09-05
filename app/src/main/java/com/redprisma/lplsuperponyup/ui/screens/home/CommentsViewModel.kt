package com.redprisma.lplsuperponyup.ui.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redprisma.lplsuperponyup.data.repository.CommentsRepository
import com.redprisma.lplsuperponyup.data.util.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val commentsRepository: CommentsRepository,  // Injected repository dependency
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _homeState = mutableStateOf<CommentListState>(CommentListState.Initial)
    val homeState: MutableState<CommentListState> = _homeState

    fun loadComments() {
        viewModelScope.launch(ioDispatcher) {
            _homeState.value = CommentListState.Loading // Show loading state
            commentsRepository.fetchComments()
                .collect { result ->
                    when (result) {
                        is DataResult.Error -> {
                            _homeState.value = CommentListState.Error(appError = result.appError)
                        }

                        is DataResult.Success -> {
                            _homeState.value = CommentListState.Success(
                                comments = result.data,
                                fromCache = result.fromCache,
                                error = result.error
                            )
                        }
                    }
                }
        }
    }
}