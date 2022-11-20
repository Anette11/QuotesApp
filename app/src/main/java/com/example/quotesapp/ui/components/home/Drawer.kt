package com.example.quotesapp.ui.components.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.quotesapp.R

@Composable
fun Drawer(
    items: List<DrawerItem>,
    onItemClick: (DrawerItem.Item) -> Unit
) = LazyColumn(
    modifier = Modifier
        .fillMaxSize()
        .padding(dimensionResource(id = R.dimen._16dp))
) {
    items(items = items) { item ->
        when (item) {
            is DrawerItem.Menu -> DrawerMenuItem(item = item)
            is DrawerItem.Item -> {
                DrawerItemBody(
                    item = item,
                    onItemClick = { onItemClick(item) }
                )
            }
        }
    }
}