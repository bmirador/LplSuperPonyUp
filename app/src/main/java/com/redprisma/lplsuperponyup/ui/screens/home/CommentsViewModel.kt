package com.redprisma.lplsuperponyup.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redprisma.lplsuperponyup.data.repository.CommentsRepository
import com.redprisma.lplsuperponyup.data.util.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// ViewModel for managing comment-related UI state
@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val commentsRepository: CommentsRepository // Injected repository dependency
) : ViewModel() {

    // Internal mutable state for UI
    private val _homeState = MutableStateFlow<HomeState>(HomeState.Initial)

    // Exposed immutable state for observers (e.g., UI)
    val homeState: StateFlow<HomeState> = _homeState

    // Loads comments from repository and updates the UI state accordingly
    fun loadComments() {
        viewModelScope.launch(Dispatchers.IO) {
            _homeState.value = HomeState.Loading // Show loading state
            commentsRepository.fetchComments()
                .collect { result ->
                    when (result) {
                        is DataResult.Error -> {
                            _homeState.value = HomeState.Error(appError = result.appError)
                        }

                        is DataResult.Success -> {
                            _homeState.value = HomeState.Success(
                                comments = result.data,
                                fromCache = result.fromCache
                            )
                        }
                    }
                }
        }
    }
}