package com.example.quotesapp.ui.components.home

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quotesapp.R

@Composable
fun QuoteCard(
    text: String?,
    author: String?
) = Card(
    modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
    shape = RoundedCornerShape(4.dp),
    elevation = 4.dp
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Icon(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.ic_quote),
            contentDescription = "Quote"
        )
        Text(
            text = "$text",
            fontFamily = FontFamily(Font(R.font.playfair_display_bold)),
            fontSize = 20.sp
        )
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "$author",
            fontFamily = FontFamily(Font(R.font.playfair_display_regular)),
            fontSize = 18.sp,
            textAlign = TextAlign.End
        )
    }
}

@Preview()
@Composable
fun QuoteCardPreview() =
    QuoteCard(
        text = "Some quote description, some quote description, some quote description ",
        author = "Author Author"
    )