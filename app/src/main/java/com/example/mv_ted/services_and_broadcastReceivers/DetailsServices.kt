package com.example.mv_ted.services_and_broadcastReceivers

import android.app.IntentService
import android.content.Intent
import android.os.Parcelable
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.mv_ted.models.data.model.*
import com.example.mv_ted.models.data.model.rest.rest_mdbApi.MovieResultDTO
import com.example.mv_ted.models.data.model.rest.rest_mdbApi.MoviesLoader
import java.util.ArrayList


class DetailsServices(name: String = "Details_Services") : IntentService(name) {
    private val broadcastIntent = Intent(DETAILS_INTENT_FILTER)
    override fun onHandleIntent(intent: Intent?){
        if (intent == null){
            onEmptyIntent()
        } else{
            onResponse(MoviesLoader.loadMovies(uriUpComing))
        }
    }

    private fun onEmptyIntent() {
        putLoadResult(DETAILS_EMPTY_INTENT_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun putLoadResult(result: String) {
           broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, result)
    }

    private fun onResponse(loadMovies: MutableList<MovieResultDTO>?) {
        if (loadMovies == null){
            onEmptyResponse()
        } else {
            onSuccessResponse(loadMovies)
        }
    }

    private fun onEmptyResponse() {
        putLoadResult(DETAILS_RESPONSE_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onSuccessResponse(loadMovies: MutableList<MovieResultDTO>) {
        putLoadResult(DETAILS_RESPONSE_SUCCESS_EXTRA)
        broadcastIntent.putParcelableArrayListExtra(DETAILS_LOADED_MOVIES, loadMovies as ArrayList<out Parcelable>)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }
}