package com.example.quotesapp.ui.components.search.common

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.example.quotesapp.R
import com.example.quotesapp.data.remote.dto.ResultDto
import com.example.quotesapp.ui.components.common.QuoteCard
import com.example.quotesapp.ui.components.search.ChipItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchScreen(
    quotes: LazyPagingItems<ResultDto>,
    chips: List<ChipItem>,
    onChipSelected: (Int) -> Unit,
    onClearAll: () -> Unit
) = Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onClearAll
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_clear_all),
                    contentDescription = stringResource(id = R.string.icon_clear_all_description),
                    tint = if (chips.find { chipItem ->
                            chipItem.isSelected
                        }?.isSelected == true) Color.DarkGray else Color.LightGray
                )
            }
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                state = rememberLazyListState()
            ) {
                itemsIndexed(items = chips) { index, chip ->
                    Chip(
                        modifier = Modifier.padding(dimensionResource(id = R.dimen._4dp)),
                        onClick = { onChipSelected(index) },
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