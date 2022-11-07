package com.example.quotesapp.ui.components.random

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.quotesapp.R

@Composable
fun RandomTopAppBar(
    onMenuIconClick: () -> Unit,
    onRefreshClick: () -> Unit,
    onNotificationClick: () -> Unit
) = TopAppBar(
    title = {
        Text(
            text = "Random",
            fontFamily = FontFamily(Font(R.font.rubik_regular))
        )
    },
    navigationIcon = {
        IconButton(
            onClick = onMenuIconClick
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }
    },
    actions = {
        IconButton(
            onClick = onRefreshClick
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Refresh"
            )
        }
        IconButton(
            onClick = onNotificationClick
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notification"
            )
        }
    }
)