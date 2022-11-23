package com.example.quotesapp.ui.components.search.by_tag

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.quotesapp.ui.components.search.common.SearchScreen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SearchByTagScreen(
    searchByTagViewModel: SearchByTagViewModel = hiltViewModel()
) {
    val chips by searchByTagViewModel.chips
    val context = LocalContext.current
    val quotes = searchByTagViewModel.quotes.collectAsLazyPagingItems()

    LaunchedEffect(true) {
        searchByTagViewModel.error.collectLatest {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    SearchScreen(
        quotes = quotes,
        chips = chips,
        onChipSelected = { index ->
            searchByTagViewModel.onChipSelected(index = index)
        },
        onClearAll = { searchByTagViewModel.onClearAll() }
    )
}