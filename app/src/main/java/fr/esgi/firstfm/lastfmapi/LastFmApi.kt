package fr.esgi.firstfm.lastfmapi

import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LastFmApi {
    private var lastFmApiServices: LastFmApiServices? = null

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://ws.audioscrobbler.com/2.0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        lastFmApiServices = retrofit.create(LastFmApiServices::class.java)
    }

    fun retrieveTrackInfo(artist: String, title: String, callback: Callback<LastFmApiTrackGetInfoResponse>)
    {
        val call = lastFmApiServices?.retrieveTrackInfo(artist, title, "b61ee97cfc7ed80dad34909f0c980b8e")
        call?.enqueue(callback)
    }
}