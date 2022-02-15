package com.movieapp.mv_ted.data.datasource.cloudsource.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BackendRepo {
    val api: BackendApi by lazy {
          val adapter = Retrofit.Builder()
              .baseUrl(APIUtils.baseUrl)
              .addConverterFactory(GsonConverterFactory.create())
              .client(APIUtils.getOkHTTPBuilderWithHeaders())
              .build()
         adapter.create(BackendApi::class.java)
    }
}