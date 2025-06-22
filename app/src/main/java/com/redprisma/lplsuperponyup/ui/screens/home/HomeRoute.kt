package com.redprisma.lplsuperponyup.ui.screens.home

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class HomeRoute(val route: String) : NavKey {
    @Serializable
    data object Home : HomeRoute(route = "home")
    @Serializable
    data object CommentDetail : HomeRoute(route = "home")
}