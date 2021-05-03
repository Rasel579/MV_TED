package com.example.mv_ted.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mv_ted.models.data.model.RepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(private val liveData: MutableLiveData<Any> = MutableLiveData(),
                    private var repositoryImpl: RepositoryImpl = RepositoryImpl()
) : ViewModel() {
    // TODO: Implement the ViewModel
    fun getData() = liveData
    fun getMovieData() = getDataFromLocalSource()
    private fun getDataFromLocalSource() {
        liveData.value = AppState.Loading
        Thread{
            sleep(1000)
            liveData.postValue(AppState.Success(repositoryImpl))
        }.start()

    }
}