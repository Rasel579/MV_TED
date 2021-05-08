package com.example.mv_ted.models.data.model

import java.util.*

 class RepositoryImpl() : Repository {
     override fun getDataFromLocalStorage() = getDataMovie()
     override fun getDataFromMovieAPI() = getDataMovie()

 }
