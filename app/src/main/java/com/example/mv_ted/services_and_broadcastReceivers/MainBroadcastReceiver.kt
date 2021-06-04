package com.example.mv_ted.services_and_broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import java.lang.StringBuilder

class MainBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        StringBuilder().apply {
            append(MSG_FROM_BROADCAST_RECEIVER)
            append(" Action ${intent?.action}")
            toString().also {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }
    companion object{
        const val MSG_FROM_BROADCAST_RECEIVER = "Message from Android OS"
    }
}