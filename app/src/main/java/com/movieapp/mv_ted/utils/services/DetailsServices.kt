package com.movieapp.mv_ted.utils.services

import android.app.IntentService
import android.content.Intent
import android.os.Parcelable
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.movieapp.mv_ted.domain.models.response.movie.MovieResponse
import com.movieapp.mv_ted.data.datasource.cloudsource.rest_mdbApi.MoviesLoader
import com.movieapp.mv_ted.utils.*
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

    private fun onResponse(loadMovies: MutableList<MovieResponse>?) {
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

    private fun onSuccessResponse(loadMovies: MutableList<MovieResponse>) {
        putLoadResult(DETAILS_RESPONSE_SUCCESS_EXTRA)
        broadcastIntent.putParcelableArrayListExtra(DETAILS_LOADED_MOVIES, loadMovies as ArrayList<out Parcelable>)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }
}