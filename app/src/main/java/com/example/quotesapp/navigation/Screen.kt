package com.example.quotesapp.navigation

sealed class Screen(
    val route: String
) {
    object HomeScreen : Screen(route = "home_screen")
    object RandomScreen : Screen(route = "random_screen")
}