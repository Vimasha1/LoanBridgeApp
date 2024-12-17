package com.example.loanbridgeapp
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val taskName = intent.getStringExtra("taskName") ?: "Task"
        createNotification(context, taskName)
    }

    private fun createNotification(context: Context, taskName: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "TASK_REMINDER_CHANNEL"

        // Create notification channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Task Reminders", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        // Build the notification
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.appicon1) // Replace with your notification icon
            .setContentTitle("Task Reminder")
            .setContentText("Reminder: You have a task due tomorrow - $taskName.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        notificationManager.notify(1001, notificationBuilder.build()) // Use a unique notification ID
    }


}