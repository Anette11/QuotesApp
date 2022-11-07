package com.example.quotesapp.ui.components.random

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.quotesapp.ui.components.common.QuoteCard
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RandomScreen(
    randomViewModel: RandomViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val context = LocalContext.current
    val randomQuote by randomViewModel.randomQuote.collectAsState()
    val isLoading by randomViewModel.isLoading.collectAsState()

    LaunchedEffect(true) {
        randomViewModel.error.collectLatest {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            RandomTopAppBar(
                onMenuIconClick = { navController.navigateUp() },
                onRefreshClick = {
                    randomViewModel.getRandomQuote()
                },
                onNotificationClick = {}
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                randomQuote?.let { randomQuote ->
                    QuoteCard(
                        text = randomQuote.content,
                        author = randomQuote.author
                    )
                }
            }
            if (isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}
