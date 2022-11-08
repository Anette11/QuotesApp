package com.example.quotesapp.ui.components.random

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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
    selectedTime: String?,
    onTimeSelected: (Date) -> Unit,
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit
) = ModalBottomSheetLayout(
    sheetState = sheetState,
    sheetShape = RoundedCornerShape(
        topStart = dimensionResource(id = R.dimen._8dp),
        topEnd = dimensionResource(id = R.dimen._8dp)
    ),
    sheetContent = {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen._16dp))
        ) {
            val context = LocalContext.current
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.notification_bottom_sheet_title),
                fontFamily = FontFamily(Font(R.font.rubik_bold)),
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen._16dp)))
            TextField(
                textStyle = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.rubik_regular))
                ),
                modifier = Modifier.fillMaxWidth(),
                value = selectedTime ?: "",
                onValueChange = {},
                readOnly = true,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.notification_bottom_sheet_placeholder),
                        fontFamily = FontFamily(Font(R.font.rubik_regular)),
                        fontSize = 18.sp
                    )
                },
                enabled = false,
                trailingIcon = {
                    IconButton(
                        onClick = {
                            TimePickerDialog(
                                context,
                                { _, hour, minute ->
                                    val calendar = Calendar.getInstance()
                                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                                    calendar.set(Calendar.MINUTE, minute)
                                    val date = calendar.time
                                    onTimeSelected(date)
                                },
                                selectedTime?.substring(0..1)?.toInt()
                                    ?: Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                                selectedTime?.substring(3..4)?.toInt()
                                    ?: Calendar.getInstance().get(Calendar.MINUTE),
                                true
                            ).show()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_time),
                            contentDescription = stringResource(id = R.string.icon_set_time_description)
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen._16dp)))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = onCancelClick
                ) {
                    Text(
                        text = "Cancel",
                        fontFamily = FontFamily(Font(R.font.rubik_regular)),
                        fontSize = 18.sp
                    )
                }
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen._16dp)))
                OutlinedButton(
                    enabled = !selectedTime.isNullOrEmpty(),
                    modifier = Modifier.weight(1f),
                    onClick = onConfirmClick
                ) {
                    Text(
                        text = "OK",
                        fontFamily = FontFamily(Font(R.font.rubik_regular)),
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
) {}