package com.example.crashslytics

import android.content.Context
import android.util.Log
import android.widget.Toast
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class KTORClient(private val context: Context)
{
    private val client =  HttpClient(CIO){
        install(JsonFeature){
            serializer = GsonSerializer()
        }
        install(HttpTimeout)

        engine {
            requestTimeout =  10000// 0 to disable, or a millisecond value to fit your needs
        }
    }

    suspend fun getData(): List<MovieData> {
        try{
            val data : List<MovieData> = client.get("https://howtodoandroid.com/movielist.json")
            Log.i("Ayush", "Data ${data.size}")
            return data
        }
        catch (e:Exception)
        {
            Log.i("Ayush",e.message?:"Thee exception is null here")
            withContext(Dispatchers.Main){
                Toast.makeText(context,e.message,Toast.LENGTH_LONG).show()
            }
            return emptyList()
        }

    }

}