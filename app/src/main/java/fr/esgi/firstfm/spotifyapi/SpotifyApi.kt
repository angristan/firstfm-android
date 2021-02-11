package fr.esgi.firstfm.spotifyapi

import androidx.appcompat.app.AppCompatActivity
import fr.esgi.firstfm.data.Spotify
import fr.esgi.firstfm.entity.SpotifyArtistSearchResponse
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URLEncoder

object SpotifyApi {
    private var spotifyApiServices: SpotifyApiServices? = null

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spotify.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        spotifyApiServices = retrofit.create(SpotifyApiServices::class.java)
    }

    fun searchArtistInfo(
        activity: AppCompatActivity,
        artist: String?,
        callback: Callback<SpotifyArtistSearchResponse>
    ) {

        val spotifyToken = Spotify().getSpotifyToken(activity)
        val encodedArtist = URLEncoder.encode(artist, "utf-8")

        val call = spotifyApiServices?.retrieveArtistInfo("Bearer $spotifyToken", encodedArtist)
        call?.enqueue(callback)
    }
}