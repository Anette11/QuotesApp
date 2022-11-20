package com.example.quotesapp.ui.components.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .clickable { onItemClick() }
        .padding(vertical = dimensionResource(id = R.dimen._16dp)),
    verticalAlignment = Alignment.CenterVertically
) {
    Icon(
        imageVector = item.icon,
        contentDescription = item.contentDescription
    )
    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen._16dp)))
    Text(
        text = item.text,
        fontSize = 18.sp,
        fontFamily = FontFamily(Font(R.font.rubik_regular))
    )
}