package com.example.mv_ted.models.data.model

import android.view.View
import com.example.mv_ted.view_model.MainViewModel
import com.google.android.material.snackbar.Snackbar
import java.net.URL

val uriNow = URL("https://api.themoviedb.org/3/movie/popular")
val uriUpComing = URL("https://api.themoviedb.org/3/movie/upcoming")
fun View.showSnackBar(message: String, length : Int){
    Snackbar.make(this, message, length).show()
}

fun View.showSnackBar(message: String, length: Int, actionText: String, viewModel : MainViewModel){
    Snackbar.make(this, message, length)
        .setAction(actionText) {
            viewModel.getMovieData()
        }.show()
}