package fr.esgi.firstfm.spotifyapi

import fr.esgi.firstfm.entity.SpotifyArtistSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SpotifyApiServices {
    @GET("search?type=artist")
    fun retrieveArtistInfo(
        @Header("Authorization") authorization: String,
        @Query("q") encodedArtist: String
    ): Call<SpotifyArtistSearchResponse>
}