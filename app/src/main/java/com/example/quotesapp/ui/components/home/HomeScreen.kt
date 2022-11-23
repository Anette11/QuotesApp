package com.example.quotesapp.ui.components.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.quotesapp.R
import com.example.quotesapp.navigation.Screen
import com.example.quotesapp.ui.components.common.QuoteCard
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val quotes = homeViewModel.quotes.collectAsLazyPagingItems()
    val showHomeDialog by homeViewModel.showHomeDialog
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
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
                isMenuOpened = scaffoldState.drawerState.isOpen,
                onClearCacheClick = {
                    homeViewModel.updateShowHomeDialog()
                }
            )
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                drawerContent = {
                    Drawer(
                        items = listOf(
                            DrawerItem.Menu(
                                text = stringResource(id = R.string.menu_item_menu)
                            ),
                            DrawerItem.Item(
                                text = stringResource(id = R.string.menu_item_search),
                                route = Screen.Search.route,
                                icon = Icons.Default.Search,
                                contentDescription = stringResource(id = R.string.menu_item_search)
                            ),
                            DrawerItem.Item(
                                text = stringResource(id = R.string.menu_item_random),
                                route = Screen.Random.route,
                                icon = Icons.Default.Notifications,
                                contentDescription = stringResource(id = R.string.menu_item_random)
                            )
                        ),
                        onItemClick = { item ->
                            coroutineScope.launch {
                                scaffoldState.drawerState.close()
                            }
                            navController.navigate(route = item.route) {
                                launchSingleTop = true
                            }
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
        if (showHomeDialog) {
            HomeDialog(
                onDismiss = {
                    homeViewModel.updateShowHomeDialog()
                },
                onConfirm = {
                    homeViewModel.updateShowHomeDialog()
                    homeViewModel.deleteAllQuotes()
                }
            )
        }
    }
}