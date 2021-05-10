package com.example.mv_ted.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mv_ted.models.data.model.RepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(private val liveData: MutableLiveData<Any> = MutableLiveData(),
                    private var repository: RepositoryImpl = RepositoryImpl()
) : ViewModel() {
    fun getData() = liveData
    fun getMovieData() = getDataFromLocalSource()
    private fun getDataFromLocalSource() {
        liveData.value = AppState.Loading
        Thread{
            sleep(TIME_TO_SLEEP.toLong())
            liveData.postValue(AppState.Success(repository.getDataFromLocalStorage()))
        }.start()
    }

    companion object {
        const val  TIME_TO_SLEEP = 10000
    }
}