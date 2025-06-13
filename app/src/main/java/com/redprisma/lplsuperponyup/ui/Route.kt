package com.redprisma.lplsuperponyup.ui

sealed class Route(val route: String) {
    //Main entry point of the app
    data object Home : Route(route = "home")
    //Possible route that shows more info
    data object CommentDetail : Route(route = "commentDetail") {
        fun createRoute(userId: String) = "profile_screen/$userId"
    }
    //Wanted to add more things but it is too late
    data object SecretRoute : Route(route = "secret")
}
