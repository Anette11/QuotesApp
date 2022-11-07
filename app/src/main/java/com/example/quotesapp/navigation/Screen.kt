package com.example.quotesapp.navigation

sealed class Screen(
    val route: String
) {
    object Quotes : Screen(route = "quotes_screen")
    object Search : Screen(route = "search_screen")
    object Random : Screen(route = "random_screen")
}