package com.movieapp.mv_ted.models.data.model.rest.retrofit_rest

import com.movieapp.mv_ted.BuildConfig
import com.movieapp.mv_ted.models.data.model.uriRetroApi
import okhttp3.Dns
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object APIUtils {
   const val baseUrl = uriRetroApi
   fun getOkHTTPBuilderWithHeaders() : OkHttpClient {
     val httpClient = OkHttpClient.Builder()
      httpClient.apply {
          dns(Dns.SYSTEM)
          connectTimeout(20, TimeUnit.SECONDS)
          readTimeout(20, TimeUnit.SECONDS)
          writeTimeout(20, TimeUnit.SECONDS)
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