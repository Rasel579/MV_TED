package com.movieapp.mv_ted.models.data.model

import android.view.View
import com.movieapp.mv_ted.view_model.MainViewModel
import com.google.android.material.snackbar.Snackbar
import java.net.URL
import java.util.*

val uriNow = URL("https://api.themoviedb.org/3/movie/popular")
val uriUpComing = URL("https://api.themoviedb.org/3/movie/upcoming")
const val imageUri = "https://image.tmdb.org/t/p/w500"
const val uriRetroApi = "https://api.themoviedb.org/3/"
const val TAG = "After broadcasting"

fun View.showSnackBar(message: String, length : Int){
    Snackbar.make(this, message, length).show()
}

fun View.showSnackBar(message: String, length: Int, actionText: String, viewModel : MainViewModel){
    Snackbar.make(this, message, length)
        .setAction(actionText) {
            viewModel.getMovieData()
        }.show()
}

//fake data
fun getDataMovie(): MutableList<Movie> {
    return mutableListOf(
        Movie("Justice League", "${Calendar.getInstance()[Calendar.YEAR]} ${Calendar.getInstance()[Calendar.MONTH]} ${Calendar.getInstance()[Calendar.DAY_OF_MONTH]}", "", ""),
        Movie("Movie_Some", "${Calendar.getInstance()[Calendar.YEAR]} ${Calendar.getInstance()[Calendar.MONTH]} ${Calendar.getInstance()[Calendar.DAY_OF_MONTH]}", "R.drawable.movie_poster",""),
        Movie("Avengers", "${Calendar.getInstance()[Calendar.YEAR]} ${Calendar.getInstance()[Calendar.MONTH]} ${Calendar.getInstance()[Calendar.DAY_OF_MONTH]}", "R.drawable.avengers",""),
        Movie("Kool", "${Calendar.getInstance()[Calendar.YEAR]} ${Calendar.getInstance()[Calendar.MONTH]} ${Calendar.getInstance()[Calendar.DAY_OF_MONTH]}", "R.drawable.avengers",""),
        Movie("Black Widow", "${Calendar.getInstance()[Calendar.YEAR]} ${Calendar.getInstance()[Calendar.MONTH]} ${Calendar.getInstance()[Calendar.DAY_OF_MONTH]}", "R.drawable.avengers",""),
        Movie("Mortal Kombat", "${Calendar.getInstance()[Calendar.YEAR]} ${Calendar.getInstance()[Calendar.MONTH]} ${Calendar.getInstance()[Calendar.DAY_OF_MONTH]}", "R.drawable.avengers",""),
        Movie("Scary Movie", "${Calendar.getInstance()[Calendar.YEAR]} ${Calendar.getInstance()[Calendar.MONTH]} ${Calendar.getInstance()[Calendar.DAY_OF_MONTH]}", "R.drawable.avengers",""),
        Movie("Spiral", "${Calendar.getInstance()[Calendar.YEAR]} ${Calendar.getInstance()[Calendar.MONTH]} ${Calendar.getInstance()[Calendar.DAY_OF_MONTH]}", "R.drawable.avengers",""),
        Movie("US", "${Calendar.getInstance()[Calendar.YEAR]} ${Calendar.getInstance()[Calendar.MONTH]} ${Calendar.getInstance()[Calendar.DAY_OF_MONTH]}", "R.drawable.avengers",""),
        Movie("Wonder Woman", "${Calendar.getInstance()[Calendar.YEAR]} ${Calendar.getInstance()[Calendar.MONTH]} ${Calendar.getInstance()[Calendar.DAY_OF_MONTH]}", "R.drawable.avengers",""),
        Movie("X - Mens", "${Calendar.getInstance()[Calendar.YEAR]} ${Calendar.getInstance()[Calendar.MONTH]} ${Calendar.getInstance()[Calendar.DAY_OF_MONTH]}", "R.drawable.avengers","")
    )
}

// response for broadcast receiver
const val DETAILS_INTENT_FILTER = "Details intent filter"
const val DETAILS_EMPTY_INTENT_EXTRA = "Intent is empty"
const val DETAILS_LOAD_RESULT_EXTRA = "Load result"
const val DETAILS_RESPONSE_EMPTY_EXTRA = "Response is empty"
const val DETAILS_RESPONSE_SUCCESS_EXTRA = "SUCCESS"
const val DETAILS_LOADED_MOVIES = "Loaded Movies"