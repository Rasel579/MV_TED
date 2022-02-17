package com.movieapp.mv_ted.presentation.maps

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.movieapp.mv_ted.domain.repository.Repository
import com.movieapp.mv_ted.data.repository.RepositoryImpl
import com.movieapp.mv_ted.domain.models.response.movie.Film
import com.movieapp.mv_ted.domain.AppState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel (
    val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private var repository: Repository = RepositoryImpl()
) : ViewModel() {
    fun getCountryOfFilm(movieId: String){
        liveData.value = AppState.Loading
        val callback = object : Callback<Film>{
            override fun onResponse(call: Call<Film>, response: Response<Film>) {
                if (response.isSuccessful){
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
}