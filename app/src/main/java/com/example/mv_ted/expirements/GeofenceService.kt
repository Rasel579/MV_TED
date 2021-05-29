package com.example.mv_ted.expirements

import android.app.IntentService
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class GeofenceService(name: String = "Geofence") : IntentService(name) {
    private val broadcastIntent = Intent("GeofenceService")
    override fun onHandleIntent(intent: Intent?) {
        Log.e("GeofenceIntent", "Great")
        LocalBroadcastManager.getInstance(baseContext).sendBroadcast(broadcastIntent)

    }

}
