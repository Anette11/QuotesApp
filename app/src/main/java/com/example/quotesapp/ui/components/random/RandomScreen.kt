package com.example.quotesapp.ui.components.random

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.work.*
import com.example.quotesapp.ui.components.common.QuoteCard
import com.example.quotesapp.work.NotificationWorker
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RandomScreen(
    randomViewModel: RandomViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val context = LocalContext.current
    val randomQuote by randomViewModel.randomQuote.collectAsState()
    val isLoading by randomViewModel.isLoading.collectAsState()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val selectedTime by randomViewModel.selectedTime

    LaunchedEffect(true) {
        randomViewModel.error.collectLatest {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            RandomTopAppBar(
                onMenuIconClick = { navController.navigateUp() },
                onRefreshClick = { randomViewModel.getRandomQuote() },
                onNotificationClick = {
                    coroutineScope.launch {
                        if (!sheetState.isVisible) sheetState.show() else sheetState.hide()
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                randomQuote?.let { randomQuote ->
                    QuoteCard(
                        text = randomQuote.content,
                        author = randomQuote.author
                    )
                }
            }
            NotificationModalBottomSheetLayout(
                sheetState = sheetState,
                selectedTime = selectedTime,
                onTimeSelected = { date ->
                    randomViewModel.onValueChange(date)
                },
                onCancelClick = {
                    coroutineScope.launch {
                        sheetState.hide()
                    }
                },
                onConfirmClick = {
                    coroutineScope.launch {
                        sheetState.hide()
                    }
                    val notificationRequest =
                        PeriodicWorkRequestBuilder<NotificationWorker>(24, TimeUnit.HOURS)
//                        PeriodicWorkRequestBuilder<NotificationWorker>(15, TimeUnit.MINUTES)
                            .setConstraints(
                                Constraints.Builder()
                                    .setRequiredNetworkType(NetworkType.CONNECTED)
                                    .build()
                            )
                            .setInitialDelay(randomViewModel.findInitialDelay(), TimeUnit.SECONDS)
                            .build()
                    val workManager = WorkManager.getInstance(context)
//                    workManager.enqueue(notificationRequest)
                    workManager.enqueueUniquePeriodicWork(
                        NotificationWorker.uniqueWorkName,
                        ExistingPeriodicWorkPolicy.KEEP,
                        notificationRequest
                    )
                }
            )
            if (isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}
