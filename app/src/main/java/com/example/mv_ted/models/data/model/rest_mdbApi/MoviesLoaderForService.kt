package com.example.mv_ted.models.data.model.rest_mdbApi

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.mv_ted.BuildConfig
import com.example.mv_ted.models.data.model.rest_mdbApi.MovieDTO
import com.example.mv_ted.models.data.model.rest_mdbApi.MovieResultDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

 object MoviesLoaderForService {
     private lateinit var urlConnection : HttpsURLConnection
     fun loadMovies(uri: URL): MutableList<MovieResultDTO>? {
         try {
               urlConnection = uri.openConnection() as HttpsURLConnection
               urlConnection.requestMethod = "GET"
               urlConnection.addRequestProperty("Authorization", BuildConfig.MOVIE_DB_APIKEY)
               urlConnection.addRequestProperty("Content-Type", "application/json;charset=utf-8")
               urlConnection.readTimeout = 10000
               val bufferReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
               val lines = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
                                getLinesForOldVer(bufferReader)
                            } else{
                                getLines(bufferReader)
                            }
             val temp = Gson().fromJson(lines,MovieDTO::class.java)
             //Log.d("GSON_OBJ", temp.toString())
             return temp.results
         } catch (e: Exception){
             e.printStackTrace()
    } finally {
        urlConnection.disconnect()
    }
        return null
 }

}

@RequiresApi(Build.VERSION_CODES.N)
private fun getLines(reader: BufferedReader): String {
    return reader.lines().collect(Collectors.joining("\n"))
}
private fun getLinesForOldVer(bufferReader: BufferedReader): String {
    val rawData = StringBuilder(1024)
    var tempVar : String?
    while (bufferReader.readLine().also { tempVar = it } != null) {
        rawData.append(tempVar).append("\n")
    }

    bufferReader.close()
    //Log.d("BufferedReader", rawData.toString())
    return rawData.toString()
}