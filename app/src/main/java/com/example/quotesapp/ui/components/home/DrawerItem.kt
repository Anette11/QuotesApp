package com.example.quotesapp.ui.components.home

sealed class DrawerItem {
    data class Menu(val text: String) : DrawerItem()
    data class Item(val text: String) : DrawerItem()
}