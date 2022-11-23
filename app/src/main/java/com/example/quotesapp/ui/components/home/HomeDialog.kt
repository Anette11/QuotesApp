package com.example.quotesapp.ui.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.quotesapp.R

@Composable
fun HomeDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) = Dialog(
    onDismissRequest = { }
) {
    Surface(
        shape = RoundedCornerShape(dimensionResource(id = R.dimen._4dp)),
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Transparent)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .padding(
                        top = dimensionResource(id = R.dimen._16dp),
                        start = dimensionResource(id = R.dimen._16dp),
                        end = dimensionResource(id = R.dimen._16dp)
                    ),
                text = stringResource(id = R.string.home_dialog_title),
                fontFamily = FontFamily(Font(R.font.rubik_bold)),
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier
                    .padding(
                        top = dimensionResource(id = R.dimen._16dp),
                        start = dimensionResource(id = R.dimen._16dp),
                        end = dimensionResource(id = R.dimen._16dp)
                    ),
                text = stringResource(id = R.string.home_dialog_description),
                fontFamily = FontFamily(Font(R.font.rubik_regular)),
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen._16dp)))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Gray.copy(alpha = 0.1f)),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextButton(
                    onClick = onDismiss
                ) {
                    Text(
                        text = stringResource(id = R.string.home_dialog_cancel_button),
                        fontFamily = FontFamily(Font(R.font.rubik_regular)),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }
                TextButton(
                    onClick = onConfirm
                ) {
                    Text(
                        text = stringResource(id = R.string.home_dialog_confirm_button),
                        fontFamily = FontFamily(Font(R.font.rubik_regular)),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}