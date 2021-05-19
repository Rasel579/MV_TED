package com.example.mv_ted.models.data.model.rest.retrofit_rest

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