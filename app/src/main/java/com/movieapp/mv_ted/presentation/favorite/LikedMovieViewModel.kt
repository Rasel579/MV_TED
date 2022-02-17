package com.movieapp.mv_ted.presentation.favorite

import com.movieapp.mv_ted.data.repository.RepositoryImpl
import com.movieapp.mv_ted.domain.AppState
import com.movieapp.mv_ted.domain.repository.Repository
import com.movieapp.mv_ted.presentation.core.BaseViewModel
import kotlinx.coroutines.launch

class LikedMovieViewModel(
    private val repository: Repository = RepositoryImpl()
) : BaseViewModel() {

    override fun getData() {
        liveData.value = AppState.Loading
        viewModelCoroutineScope.launch {
            liveData.postValue(
                AppState.SuccessLike(
                    repository.getAllLikesMovies()
                )
            )
        }
    }

    override fun handleError(throwable: Throwable) {
        liveData.postValue(
            AppState.Error(throwable)
        )
    }


}