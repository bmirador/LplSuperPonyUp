package com.redprisma.lplsuperponyup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redprisma.lplsuperponyup.data.local.models.Comment
import com.redprisma.lplsuperponyup.data.repository.CommentsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
        viewModelScope.launch {
            _homeState.value = HomeState.Loading // Show loading state
            commentsRepository.fetchComments()
                .collect { result ->
                    result
                        .onSuccess { comments ->
                            _homeState.value = HomeState.Success(comments) // Show data on success
                        }
                        .onFailure { exception ->
                            _homeState.value = HomeState.Error(exception.message ?: "Unknown error") // Show error message
                            Log.e("CommentsViewModel", "Failed to load comments", exception)
                        }
                }
        }
    }
}
