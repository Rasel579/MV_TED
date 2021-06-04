package com.example.mv_ted.services_and_broadcastReceivers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

class NotificationImpl {
    companion object {
        fun createNotification(context: Context, channelId : String, icon : Int, title : String, contentText : String, notificationId :Int){
            val notificationBuilder = NotificationCompat.Builder(
                context,
                channelId
            ).apply {
                setSmallIcon(icon)
                setContentTitle(title)
                setContentText(contentText)
                priority = NotificationCompat.PRIORITY_DEFAULT
            }
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                createNotificationChannel(notificationManager, channelId)
            }
            notificationManager.notify(notificationId, notificationBuilder.build())

        }

        @RequiresApi(Build.VERSION_CODES.O)
        private fun createNotificationChannel(notificationManager: NotificationManager, channelId: String) {
            val name = "channel_name"
            val  descriptionText = "description_text"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }
     }
    }
