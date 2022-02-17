package com.movieapp.mv_ted.presentation.maps

import com.movieapp.mv_ted.domain.AppState
import com.movieapp.mv_ted.domain.models.response.movie.Film
import com.movieapp.mv_ted.domain.repository.Repository
import com.movieapp.mv_ted.presentation.core.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel(
    private var repository: Repository
) : BaseViewModel() {
    fun getCountryOfFilm(movieId: String) {
        liveData.value = AppState.Loading
        val callback = object : Callback<Film> {
            override fun onResponse(call: Call<Film>, response: Response<Film>) {
                if (response.isSuccessful) {
                    liveData.postValue(
                        response.body()?.productionCompanies?.let {
                            AppState.SuccessFilmCountry(
                                it.first().name
                            )
                        }
                    )
                }
            }

            override fun onFailure(call: Call<Film>, t: Throwable) {
                t.printStackTrace()
            }

        }
        repository.getDataFromServerAboutFilm(callback, movieId)
    }

    override fun getData() {
        TODO("Not yet implemented")
    }

    override fun handleError(throwable: Throwable) {
        liveData.postValue(AppState.Error(throwable))
    }
}