package com.example.quotesapp.ui.components.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.quotesapp.R

@Composable
fun DrawerMenuItem(
    item: DrawerItem.Menu
) = Text(
    modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = dimensionResource(id = R.dimen._16dp)),
    text = item.text,
    fontSize = 20.sp,
    fontFamily = FontFamily(Font(R.font.rubik_bold)),
    textAlign = TextAlign.Center
)