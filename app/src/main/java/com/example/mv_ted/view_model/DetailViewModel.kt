package com.example.mv_ted.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mv_ted.models.data.model.Comment
import com.example.mv_ted.models.data.model.Movie
import com.example.mv_ted.models.data.model.Repository
import com.example.mv_ted.models.data.model.RepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class DetailViewModel(
    val detailLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl()) : ViewModel(), CoroutineScope by MainScope(){
      fun getAllCommentsByMovie(movieId: String){
           detailLiveData.value = AppState.Loading
          launch(Dispatchers.IO) {
               detailLiveData.postValue(AppState.SuccessDetailsFrg(
                   repository.getHistoryComments(movieId)
               ))
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