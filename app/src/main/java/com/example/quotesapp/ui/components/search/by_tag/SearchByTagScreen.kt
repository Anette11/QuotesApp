package com.example.quotesapp.ui.components.search.by_tag

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.quotesapp.R
import com.example.quotesapp.ui.components.common.QuoteCard
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchByTagScreen(
    searchByTagViewModel: SearchByTagViewModel = hiltViewModel(),
) {
    val chips by searchByTagViewModel.chips
    val context = LocalContext.current
    val quotes = searchByTagViewModel.quotes.collectAsLazyPagingItems()

    LaunchedEffect(true) {
        searchByTagViewModel.error.collectLatest {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                state = rememberLazyListState()
            ) {
                itemsIndexed(items = chips) { index, chip ->
                    Chip(
                        modifier = Modifier.padding(4.dp),
                        onClick = {
                            searchByTagViewModel.onChipSelected(index)
                        },
                        colors = ChipDefaults.chipColors(
                            backgroundColor = if (chip.isSelected) Color.DarkGray else Color.LightGray,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = chip.text,
                            fontFamily = FontFamily(Font(R.font.rubik_regular))
                        )
                    }
                }
            }
            LazyColumn {
                items(items = quotes) { quote ->
                    QuoteCard(
                        text = quote?.content,
                        author = quote?.author
                    )
                }
            }
        }
        if (
            quotes.loadState.prepend is LoadState.Loading ||
            quotes.loadState.refresh is LoadState.Loading ||
            quotes.loadState.append is LoadState.Loading
        ) {
            CircularProgressIndicator()
        }
    }
}