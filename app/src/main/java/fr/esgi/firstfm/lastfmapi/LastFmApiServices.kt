package fr.esgi.firstfm.lastfmapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFmApiServices {
    @GET("?method=track.getInfo&format=json")
    fun retrieveTrackInfo(
        @Query("artist") artist: String,
        @Query("track") track: String,
        @Query("api_key") apiKey: String
    ): Call<LastFmApiTrackGetInfoResponse>
}