package com.example.mv_ted.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mv_ted.models.data.model.Repository
import com.example.mv_ted.models.data.model.RepositoryImpl
import com.example.mv_ted.models.data.model.uriNow
import com.example.mv_ted.models.data.model.uriUpComing
import java.lang.Thread.sleep

class MainViewModel(private val liveData: MutableLiveData<Any> = MutableLiveData(),
                    private var repository: Repository = RepositoryImpl()
) : ViewModel() {
    fun getData() = liveData
    fun getMovieData() = getDataFromLocalSource()
    private fun getDataFromLocalSource() {
        liveData.value = AppState.Loading
        Thread{
            liveData.postValue(AppState.Success(
                repository.getDataFromServer(uriNow),
                repository.getDataFromServer(uriUpComing)))
        }.start()
    }
}