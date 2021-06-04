package com.example.mv_ted.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mv_ted.models.data.model.Repository
import com.example.mv_ted.models.data.model.RepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class LikedMovieViewModel (
    val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl()) : ViewModel(), CoroutineScope by MainScope() {

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