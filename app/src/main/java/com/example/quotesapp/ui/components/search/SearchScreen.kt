package com.example.quotesapp.ui.components.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun SearchScreen(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            SearchTopAppBar(
                onMenuIconClick = { navController.navigateUp() }
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            SearchTabs()
        }
    }
}