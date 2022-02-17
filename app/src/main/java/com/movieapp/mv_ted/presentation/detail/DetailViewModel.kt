package com.movieapp.mv_ted.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.movieapp.mv_ted.domain.models.Comment
import com.movieapp.mv_ted.domain.models.Movie
import com.movieapp.mv_ted.domain.repository.Repository
import com.movieapp.mv_ted.data.repository.RepositoryImpl
import com.movieapp.mv_ted.domain.AppState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class DetailViewModel(
    val detailLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl()
) : ViewModel(), CoroutineScope by MainScope(){
      fun getAllCommentsByMovie(movieId: String){
           detailLiveData.value = AppState.Loading
          launch(Dispatchers.IO) {
               detailLiveData.postValue(
                   AppState.SuccessDetailsFrg(
                       repository.getHistoryComments(movieId)
                   )
               )
          }
      }

    fun  getMovieById(movieId: String){
        detailLiveData.value = AppState.Loading
        launch ( Dispatchers.IO ){
            detailLiveData.postValue(
                AppState.SuccessMovieId(repository.getMovieById(movieId))
            )
        }
    }

    fun saveEntity(text: String, id: String) {
           Thread {
               repository.saveEntity(Comment(0, id, text))
           }.start()
    }

    fun saveLikesMovie(movie: Movie){
        Thread {
            repository.saveLikes(movie)
        }.start()
    }


}