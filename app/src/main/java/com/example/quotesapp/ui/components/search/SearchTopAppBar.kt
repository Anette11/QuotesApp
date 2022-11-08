package com.example.quotesapp.ui.components.search

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.quotesapp.R

@Composable
fun SearchTopAppBar(
    onMenuIconClick: () -> Unit
) = TopAppBar(
    title = {
        Text(
            text = stringResource(id = R.string.top_app_bar_search_name),
            fontFamily = FontFamily(Font(R.font.rubik_regular))
        )
    },
    navigationIcon = {
        IconButton(
            onClick = onMenuIconClick
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(id = R.string.icon_back_description)
            )
        }
    }
)