package com.example.mv_ted.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mv_ted.models.data.model.Repository
import com.example.mv_ted.models.data.model.RepositoryImpl
import com.example.mv_ted.models.data.model.rest.rest_mdbApi.Film
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