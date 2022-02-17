package com.movieapp.mv_ted.presentation.main

import com.movieapp.mv_ted.data.repository.RepositoryImpl
import com.movieapp.mv_ted.domain.AppState
import com.movieapp.mv_ted.domain.repository.Repository
import com.movieapp.mv_ted.presentation.core.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(
    private var repository: Repository = RepositoryImpl()
) : BaseViewModel() {
    override fun getData() {
        liveData.value = AppState.Loading
        viewModelCoroutineScope.launch {
            liveData.postValue(
                AppState.Success(repository.getDataFromServerRetrofit().results)
            )
        }
    }

    fun getPageData(page: Int) {
        viewModelCoroutineScope.launch {
            liveData.postValue(AppState.SuccessPagination(repository.getDataNextList(page).results))
        }

    }

    override fun handleError(throwable: Throwable) {
        liveData.postValue(
            AppState.Error(throwable)
        )
    }

}