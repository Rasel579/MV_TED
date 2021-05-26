package com.example.mv_ted.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mv_ted.models.data.model.Repository
import com.example.mv_ted.models.data.model.RepositoryImpl
import com.example.mv_ted.models.data.model.rest.rest_mdbApi.MovieDTO
import com.example.mv_ted.models.data.model.rest.rest_mdbApi.MovieResultDTO
import com.example.mv_ted.models.data.model.uriNow
import com.example.mv_ted.models.data.model.uriUpComing
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(val liveData: MutableLiveData<Any> = MutableLiveData(),
                    private var repository: Repository = RepositoryImpl()
) : ViewModel() {
    fun getData() = liveData
    fun getMovieData() = getDataFromLocalSource()
    fun loadMovieDataRetrofit(isAdult: Boolean) {
        liveData.value = AppState.Loading
        var moviesPlayingNow : MutableList<MovieResultDTO>? = null
        var moviesPlayingUpcoming : MutableList<MovieResultDTO>? = null
        val callbackFromRetrofitWithMoviePlayingNow = object : Callback<MovieDTO>{
            override fun onResponse(call: Call<MovieDTO>, response: Response<MovieDTO>) {
                if (response.isSuccessful){ moviesPlayingNow = response.body()?.results
                    if(!isAdult) {
                        liveData.postValue(
                            AppState.Success(
                                moviesPlayingNow,
                                moviesPlayingUpcoming
                            )
                        )
                    } else{
                        liveData.postValue(
                            AppState.Success(
                                noAdultMovies(moviesPlayingNow),
                                noAdultMovies(moviesPlayingUpcoming)
                            )
                        )
                    }
                }
            }
            override fun onFailure(call: Call<MovieDTO>, t: Throwable) {
                t.printStackTrace()
            }
        }
        val callbackFromRetrofitWithMovieUpcoming = object : Callback<MovieDTO>{
            override fun onResponse(call: Call<MovieDTO>, response: Response<MovieDTO>) {
                if (response.isSuccessful){ moviesPlayingUpcoming = response.body()?.results
                    if(!isAdult){
                        liveData.postValue(AppState.Success(
                            moviesPlayingNow,
                            moviesPlayingUpcoming
                        ))
                     } else {
                        liveData.postValue(
                            AppState.Success(
                                noAdultMovies(moviesPlayingNow),
                                noAdultMovies((moviesPlayingUpcoming))
                            )
                        )
                     }
                }
            }
            override fun onFailure(call: Call<MovieDTO>, t: Throwable) {
                t.printStackTrace()
            }
        }

        repository.getDataFromServerRetrofit(callbackFromRetrofitWithMoviePlayingNow)
        repository.getDataFromServerRetrofitUpcoming(callbackFromRetrofitWithMovieUpcoming)

    }

       private fun noAdultMovies(moviesPlayingNow: MutableList<MovieResultDTO>?): MutableList<MovieResultDTO> {
                val noAdultMovies : MutableList<MovieResultDTO> = mutableListOf()
           if (moviesPlayingNow != null) {
               for (movie in moviesPlayingNow) {
                    if (!movie.adult) {
                        noAdultMovies.add(movie)
                    }
               }
           }
           return noAdultMovies
    }

    private fun getDataFromLocalSource() {
        liveData.value = AppState.Loading
        Thread{
            liveData.postValue(AppState.Success(
                repository.getDataFromServer(uriNow),
                repository.getDataFromServer(uriUpComing)))
        }.start()
    }
}