package com.example.quotesapp.work

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.example.quotesapp.R
import com.example.quotesapp.data.remote.util.NetworkResource
import com.example.quotesapp.data.repository.QuoteRepository
import com.example.quotesapp.ui.MainActivity
import com.example.quotesapp.util.NotificationChannelCreator
import com.example.quotesapp.util.ResourceProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlin.random.Random

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val repository: QuoteRepository,
    private val resourceProvider: ResourceProvider
) : CoroutineWorker(context, workerParameters) {

    companion object {
        const val uniqueWorkName = "unique_work_name_notification"
    }

    override suspend fun doWork(): Result {
        startForegroundService()
        return when (val response = repository.getRandomQuote()) {
            is NetworkResource.Success -> {
                createNotification(
                    content = response.data?.content,
                    author = response.data?.author
                )
                Result.success()
            }
            is NetworkResource.Failure -> Result.failure()
        }
    }

    private fun startForegroundService() {
        val notification = NotificationCompat.Builder(
            context, NotificationChannelCreator.notificationChannelId
        )
            .setContentTitle(resourceProvider.getString(R.string.notification_quote_title))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentText(resourceProvider.getString(R.string.notification_quote_description))
            .build()
        setForegroundAsync(
            ForegroundInfo(123, notification)
        )
    }

    private fun createNotification(
        content: String?,
        author: String?
    ) {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            else PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notification = NotificationCompat.Builder(
            context,
            NotificationChannelCreator.notificationChannelId
        )
            .setContentTitle(resourceProvider.getString(R.string.notification_quote_title))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("$content\n-$author")
            )
            .setContentIntent(pendingIntent)
            .build()
            .apply {
                flags = Notification.FLAG_AUTO_CANCEL
            }
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(Random.nextInt(), notification)
    }
}