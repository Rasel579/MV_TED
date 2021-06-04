package com.example.mv_ted.services_and_broadcastReceivers
import android.util.Log
import com.example.mv_ted.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        val remoteData = message.data
        if (remoteData.isNotEmpty()){
            remoteData[MESSAGE_KEY]?.let { Log.e(TAG, it) }
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
        NotificationImpl.createNotification(applicationContext, CHANNEL_ID, R.drawable.ic_outline_movie_filter_24, title, message,
            NOTIFICATION_ID)
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
        private const val TAG = "FCM_Service"
    }
}