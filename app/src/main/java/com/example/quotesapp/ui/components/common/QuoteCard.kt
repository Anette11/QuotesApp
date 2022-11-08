package com.example.quotesapp.ui.components.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.quotesapp.R

@Composable
fun QuoteCard(
    text: String?,
    author: String?
) = Card(
    modifier = Modifier
        .fillMaxWidth()
        .padding(
            horizontal = dimensionResource(id = R.dimen._8dp),
            vertical = dimensionResource(id = R.dimen._4dp)
        ),
    shape = RoundedCornerShape(dimensionResource(id = R.dimen._4dp)),
    elevation = dimensionResource(id = R.dimen._4dp)
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen._16dp))
    ) {
        Icon(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.ic_quote),
            contentDescription = stringResource(id = R.string.icon_quote_description)
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "$text",
            fontFamily = FontFamily(Font(R.font.rubik_bold)),
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
        Divider(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen._8dp)))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "$author",
            fontFamily = FontFamily(Font(R.font.rubik_regular)),
            fontSize = 18.sp,
            textAlign = TextAlign.End
        )
    }
}

@Preview()
@Composable
fun QuoteCardPreview() =
    QuoteCard(
        text = stringResource(id = R.string.card_description_for_preview),
        author = stringResource(id = R.string.card_author_for_preview)
    )