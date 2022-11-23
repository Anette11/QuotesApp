package com.example.quotesapp.ui.components.home

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.quotesapp.R

@Composable
fun HomeTopAppBar(
    onMenuIconClick: () -> Unit,
    onClearCacheClick: () -> Unit,
    isMenuOpened: Boolean
) = TopAppBar(
    title = {
        Text(
            text = stringResource(id = R.string.top_app_bar_quotes_name),
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
                    contentDescription = stringResource(id = R.string.icon_close_description)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(id = R.string.icon_menu_description)
                )
            }
        }
    },
    actions = {
        IconButton(
            onClick = onClearCacheClick
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(id = R.string.icon_delete_description)
            )
        }
    }
)