package com.redprisma.lplsuperponyup.ui

sealed class Route(val route: String) {
    //Main entry point of the app
    data object Home : Route(route = "home")
}
