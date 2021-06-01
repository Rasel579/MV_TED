package com.example.mv_ted.services_and_broadcastReceivers
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.mv_ted.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        val remoteData = message.data
        if (remoteData.isNotEmpty()){
            remoteData[MESSAGE_KEY]?.let { Log.e("remoteData", it) }
            handleDataMessage(remoteData.toMap())
        }
    }

    private fun handleDataMessage(data: Map<String, String>) {
        val title = data[TITLE_KEY]
        val message = data[MESSAGE_KEY]
        if(!title.isNullOrBlank() && !message.isNullOrBlank()){
            showNotification(title, message)
        }
    }

    private fun showNotification(title: String, message: String) {
        val notificationBuilder = NotificationCompat.Builder(
            applicationContext,
            CHANNEL_ID
        ).apply {
            setSmallIcon(R.drawable.ic_outline_movie_filter_24)
            setContentTitle(title)
            setContentText(message)
            priority = NotificationManager.IMPORTANCE_DEFAULT
        }
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNotificationChanel(notificationManager)
        }
        notificationManager.notify(NOTIFICATION_ID,notificationBuilder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChanel(notificationManager: NotificationManager) {
        val name = "channel_name"
        val descriptionText = "description text"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID,name, importance).apply {
            description = descriptionText
        }
        notificationManager.createNotificationChannel(channel)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        //sent token to server api
    }
    companion object {
        private const val TITLE_KEY = "title"
        private const val MESSAGE_KEY = "message"
        private const val CHANNEL_ID = "channel_id"
        private const val NOTIFICATION_ID = 2
    }
}