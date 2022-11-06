package com.example.quotesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quotesapp.ui.components.home.HomeScreen

@Composable
fun QuotesNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.HomeScreen.route
) = NavHost(
    navController = navController,
    startDestination = startDestination
) {
    composable(
        route = Screen.HomeScreen.route
    ) {
        HomeScreen()
    }
}