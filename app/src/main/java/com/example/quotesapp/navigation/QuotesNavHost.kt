package com.example.quotesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quotesapp.ui.components.home.HomeScreen
import com.example.quotesapp.ui.components.random.RandomScreen
import com.example.quotesapp.ui.components.search.SearchScreen

@Composable
fun QuotesNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Quotes.route
) = NavHost(
    navController = navController,
    startDestination = startDestination
) {
    composable(
        route = Screen.Quotes.route
    ) {
        HomeScreen(navController = navController)
    }
    composable(
        route = Screen.Search.route
    ) {
        SearchScreen(navController = navController)
    }
    composable(
        route = Screen.Random.route
    ) {
        RandomScreen(navController = navController)
    }
}