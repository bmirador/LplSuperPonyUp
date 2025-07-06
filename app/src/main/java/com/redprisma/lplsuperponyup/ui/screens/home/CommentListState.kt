package com.redprisma.lplsuperponyup.ui.screens.home

import com.redprisma.lplsuperponyup.data.domain.Comment
import com.redprisma.lplsuperponyup.data.util.AppError

/**
 * A sealed hierarchy describing the state of the comment list screen.
 */
sealed interface CommentListState {

    /**
     * Empty state when the screen is first shown, useless
     * since we load immediately but is useful as an initial standard state
     */
    object Initial : CommentListState

    /**
     * Still loading
     */
    object Loading : CommentListState

    /**
     * Success on requesting list of comments.
     */
    data class Success(val comments: List<Comment>, val fromCache: Boolean, val error: AppError?) :
        CommentListState

    /**
     * There was an error while retrieving comments.
     */
    data class Error(val appError: AppError) : CommentListState
}