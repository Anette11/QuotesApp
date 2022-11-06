package com.example.quotesapp.ui.components.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val quotes = homeViewModel.quotes.collectAsLazyPagingItems()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = quotes,
                key = { resultDbo -> resultDbo.id }
            ) { resultDbo ->
                QuoteCard(
                    text = resultDbo?.content,
                    author = resultDbo?.author
                )
            }
        }
        if (quotes.loadState.prepend is LoadState.Loading ||
            quotes.loadState.refresh is LoadState.Loading ||
            quotes.loadState.append is LoadState.Loading
        ) {
            CircularProgressIndicator()
        }
    }
}