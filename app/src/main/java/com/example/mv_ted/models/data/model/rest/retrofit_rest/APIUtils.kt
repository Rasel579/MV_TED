package com.example.mv_ted.models.data.model.rest.retrofit_rest

import com.example.mv_ted.BuildConfig
import com.example.mv_ted.models.data.model.uriNow
import com.example.mv_ted.models.data.model.uriRetroApi
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object APIUtils {
   const val baseUrl = uriRetroApi
   fun getOkHTTPBuilderWithHeaders() : OkHttpClient {
     val httpClient = OkHttpClient.Builder()
      httpClient.apply {
          connectTimeout(10, TimeUnit.SECONDS)
          readTimeout(10, TimeUnit.SECONDS)
          writeTimeout(10, TimeUnit.SECONDS)
      }
      httpClient.addInterceptor{ chain ->
         val original = chain.request()
         val request = original.newBuilder()
            .header("Authorization", BuildConfig.MOVIE_DB_APIKEY)
            .method(original.method(), original.body())
            .build()
         chain.proceed(request)
      }
      return httpClient.build()
   }
}