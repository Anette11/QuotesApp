package com.example.quotesapp.ui.components.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.quotesapp.R

@Composable
fun DrawerItemBody(
    item: DrawerItem.Item,
    onItemClick: () -> Unit
) = Text(
    modifier = Modifier
        .fillMaxWidth()
        .clickable { onItemClick() }
        .padding(vertical = dimensionResource(id = R.dimen._16dp)),
    text = item.text,
    fontSize = 18.sp,
    fontFamily = FontFamily(Font(R.font.rubik_regular))
)