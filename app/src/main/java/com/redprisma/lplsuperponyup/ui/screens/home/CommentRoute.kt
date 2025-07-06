package com.redprisma.lplsuperponyup.ui.screens.home

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class CommentRoute() : NavKey {
    @Serializable
    data object CommentList : CommentRoute()
    @Serializable
    data object CommentDetail : CommentRoute()
}