package com.example.quotesapp.ui.components.home

import androidx.compose.ui.graphics.vector.ImageVector

sealed class DrawerItem {

    data class Menu(
        val text: String
    ) : DrawerItem()

    data class Item(
        val text: String,
        val icon: ImageVector,
        val contentDescription: String,
        val route: String
    ) : DrawerItem()
}