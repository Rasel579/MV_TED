package com.movieapp.mv_ted.presentation.detail

import com.movieapp.mv_ted.data.repository.RepositoryImpl
import com.movieapp.mv_ted.domain.AppState
import com.movieapp.mv_ted.domain.models.Comment
import com.movieapp.mv_ted.domain.models.Movie
import com.movieapp.mv_ted.domain.repository.Repository
import com.movieapp.mv_ted.presentation.core.BaseViewModel
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: Repository = RepositoryImpl()
) : BaseViewModel() {
    fun getAllCommentsByMovie(movieId: String) {
        liveData.value = AppState.Loading
        viewModelCoroutineScope.launch {
            liveData.postValue(
                AppState.SuccessDetailsFrg(
                    repository.getHistoryComments(movieId)
                )
            )
        }
    }

    fun getMovieById(movieId: String) {
        liveData.value = AppState.Loading
        viewModelCoroutineScope.launch {
            liveData.postValue(
                AppState.SuccessMovieId(repository.getMovieById(movieId))
            )
        }
    }

    fun getCredits(movieId: String) {
        viewModelCoroutineScope.launch {
            liveData.postValue(
                AppState.SuccessCredits(
                    repository.getCredits(movieId)
                )
            )
        }
    }

    fun saveEntity(text: String, id: String) {
        Thread {
            repository.saveEntity(Comment(0, id, text))
        }.start()
    }

    fun saveLikesMovie(movie: Movie) {
        Thread {
            repository.saveLikes(movie)
        }.start()
    }

    override fun getData() {
        TODO("Not yet implemented")
    }

    override fun handleError(throwable: Throwable) {
        liveData.postValue(AppState.Error(throwable))
    }


}