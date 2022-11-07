package com.example.quotesapp.ui.components.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.quotesapp.R

@Composable
fun HomeTopAppBar(
    onMenuIconClick: () -> Unit,
    isMenuOpened: Boolean
) = TopAppBar(
    modifier = Modifier.fillMaxWidth(),
    title = {
        Text(
            text = "Quotes",
            fontFamily = FontFamily(Font(R.font.rubik_regular))
        )
    },
    navigationIcon = {
        IconButton(
            onClick = onMenuIconClick
        ) {
            if (isMenuOpened) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close"
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu"
                )
            }
        }
    }
)