package com.example.quotesapp.ui.components.search.by_author

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
fun SearchByAuthorScreen(
    searchByAuthorViewModel: SearchByAuthorViewModel = hiltViewModel()
) {
    val chips by searchByAuthorViewModel.chips
    val context = LocalContext.current
    val quotes = searchByAuthorViewModel.quotes.collectAsLazyPagingItems()

    LaunchedEffect(true) {
        searchByAuthorViewModel.error.collectLatest {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    SearchScreen(
        quotes = quotes,
        chips = chips,
        onChipSelected = { index ->
            searchByAuthorViewModel.onChipSelected(index = index)
        }
    )
}