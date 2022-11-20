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
    val isChecked by randomViewModel.isChecked

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
                isChecked = isChecked,
                onCheckedChange = {
                    randomViewModel.onCheckedChange()
                    val workManager = WorkManager.getInstance(context)
                    if (isChecked) {
                        val notificationRequest =
                            PeriodicWorkRequestBuilder<NotificationWorker>(24, TimeUnit.HOURS)
                                .setConstraints(
                                    Constraints.Builder()
                                        .setRequiredNetworkType(NetworkType.CONNECTED)
                                        .build()
                                )
                                .build()
                        workManager.enqueueUniquePeriodicWork(
                            NotificationWorker.uniqueWorkName,
                            ExistingPeriodicWorkPolicy.REPLACE,
                            notificationRequest
                        )
                    } else {
                        workManager.cancelUniqueWork(NotificationWorker.uniqueWorkName)
                    }
                }
            )
            if (isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}
