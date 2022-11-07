package com.example.quotesapp.ui.components.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Drawer(
    items: List<DrawerItem>,
    onItemClick: (DrawerItem.Item) -> Unit
) = LazyColumn(
    modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
) {
    itemsIndexed(items = items) { index, item ->
        when (item) {
            is DrawerItem.Menu -> DrawerMenuItem(item = item)
            is DrawerItem.Item -> {
                DrawerItemBody(
                    item = item,
                    onItemClick = { onItemClick(item) }
                )
                if (index < items.size - 1) {
                    Divider()
                }
            }
        }
    }
}