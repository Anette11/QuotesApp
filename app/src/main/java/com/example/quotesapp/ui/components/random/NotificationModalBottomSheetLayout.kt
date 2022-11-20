package com.example.quotesapp.ui.components.random

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.quotesapp.R
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NotificationModalBottomSheetLayout(
    sheetState: ModalBottomSheetState,
    isChecked: Boolean,
    onCheckedChange: () -> Unit
) = ModalBottomSheetLayout(
    sheetState = sheetState,
    sheetShape = RoundedCornerShape(
        topStart = dimensionResource(id = R.dimen._8dp),
        topEnd = dimensionResource(id = R.dimen._8dp)
    ),
    sheetContent = {
        Row(
            modifier = Modifier.padding(dimensionResource(id = R.dimen._16dp))
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.notification_bottom_sheet_title),
                fontFamily = FontFamily(Font(R.font.rubik_bold)),
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen._16dp)))
            Switch(
                checked = isChecked,
                onCheckedChange = { onCheckedChange() }
            )
        }
    }
) {}