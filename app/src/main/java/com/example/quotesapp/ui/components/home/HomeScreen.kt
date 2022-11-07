package com.example.quotesapp.ui.components.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.quotesapp.navigation.Screen
import com.example.quotesapp.ui.components.common.QuoteCard
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val quotes = homeViewModel.quotes.collectAsLazyPagingItems()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HomeTopAppBar(
            onMenuIconClick = {
                if (scaffoldState.drawerState.isClosed) {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                } else {
                    coroutineScope.launch {
                        scaffoldState.drawerState.close()
                    }
                }
            },
            isMenuOpened = scaffoldState.drawerState.isOpen
        )
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            drawerContent = {
                Drawer(
                    items = listOf(
                        DrawerItem.Menu(text = "Menu"),
                        DrawerItem.Item(
                            text = "Quotes",
                            route = Screen.Quotes.route
                        ),
                        DrawerItem.Item(
                            text = "Search",
                            route = Screen.Search.route
                        ),
                        DrawerItem.Item(
                            text = "Random",
                            route = Screen.Random.route
                        )
                    ),
                    onItemClick = { item ->
                        coroutineScope.launch {
                            scaffoldState.drawerState.close()
                        }
                        navController.navigate(route = item.route)
                    }
                )
            },
            scaffoldState = scaffoldState
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                contentAlignment = Alignment.Center
            ) {
                LazyColumn {
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
    }
}