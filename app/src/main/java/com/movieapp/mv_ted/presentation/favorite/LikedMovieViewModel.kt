package com.movieapp.mv_ted.presentation.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.movieapp.mv_ted.domain.repository.Repository
import com.movieapp.mv_ted.data.repository.RepositoryImpl
import com.movieapp.mv_ted.domain.AppState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class LikedMovieViewModel (
    val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl()
) : ViewModel(), CoroutineScope by MainScope() {

       fun getAllMovies(){
           liveData.value = AppState.Loading
           launch(Dispatchers.IO) {
               liveData.postValue(
                   AppState.SuccessLike(
                       repository.getAllLikesMovies()
                   )
               )
           }
       }
}