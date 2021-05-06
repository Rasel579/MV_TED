package com.example.mv_ted.models.data.model

import android.os.Parcelable
import com.example.mv_ted.R
import kotlinx.android.parcel.Parcelize
import java.util.*
@Parcelize
data class Movie(val title: String, val date: String, val image: Int) : Parcelable

fun getDataMovie(): MutableList<Movie> {
    return mutableListOf(
         Movie("Justice League", "${Calendar.getInstance()[Calendar.YEAR]} ${Calendar.getInstance()[Calendar.MONTH]} ${Calendar.getInstance()[Calendar.DAY_OF_MONTH]}", R.drawable.justice_league),
         Movie("Movie_Some", "${Calendar.getInstance()[Calendar.YEAR]} ${Calendar.getInstance()[Calendar.MONTH]} ${Calendar.getInstance()[Calendar.DAY_OF_MONTH]}", R.drawable.movie_poster),
         Movie("Avengers", "${Calendar.getInstance()[Calendar.YEAR]} ${Calendar.getInstance()[Calendar.MONTH]} ${Calendar.getInstance()[Calendar.DAY_OF_MONTH]}", R.drawable.avengers),
        Movie("Kool", "${Calendar.getInstance()[Calendar.YEAR]} ${Calendar.getInstance()[Calendar.MONTH]} ${Calendar.getInstance()[Calendar.DAY_OF_MONTH]}", R.drawable.kool),
        Movie("Black Widow", "${Calendar.getInstance()[Calendar.YEAR]} ${Calendar.getInstance()[Calendar.MONTH]} ${Calendar.getInstance()[Calendar.DAY_OF_MONTH]}", R.drawable.black_widow),
        Movie("Mortal Kombat", "${Calendar.getInstance()[Calendar.YEAR]} ${Calendar.getInstance()[Calendar.MONTH]} ${Calendar.getInstance()[Calendar.DAY_OF_MONTH]}", R.drawable.mortal_kombat),
        Movie("Scary Movie", "${Calendar.getInstance()[Calendar.YEAR]} ${Calendar.getInstance()[Calendar.MONTH]} ${Calendar.getInstance()[Calendar.DAY_OF_MONTH]}", R.drawable.scary_movie),
        Movie("Spiral", "${Calendar.getInstance()[Calendar.YEAR]} ${Calendar.getInstance()[Calendar.MONTH]} ${Calendar.getInstance()[Calendar.DAY_OF_MONTH]}", R.drawable.spiral),
         Movie("US", "${Calendar.getInstance()[Calendar.YEAR]} ${Calendar.getInstance()[Calendar.MONTH]} ${Calendar.getInstance()[Calendar.DAY_OF_MONTH]}", R.drawable.us),
         Movie("Wonder Woman", "${Calendar.getInstance()[Calendar.YEAR]} ${Calendar.getInstance()[Calendar.MONTH]} ${Calendar.getInstance()[Calendar.DAY_OF_MONTH]}", R.drawable.wonder_woman),
         Movie("X - Mens", "${Calendar.getInstance()[Calendar.YEAR]} ${Calendar.getInstance()[Calendar.MONTH]} ${Calendar.getInstance()[Calendar.DAY_OF_MONTH]}", R.drawable.x_men)
     )
}

  fun getMovie(position: Int, movieList: MutableList<Movie>): Movie {
    return  movieList[position]
}

   fun getSize(movieList: MutableList<Movie>): Int {
    return movieList.size
}

   fun addMovie(movie: Movie) {
}
   fun removeMovie(movie: Movie) {
    TODO("Not yet implemented")
}

   fun updateMovie(movie: Movie, position: Int) {
    TODO("Not yet implemented")
}