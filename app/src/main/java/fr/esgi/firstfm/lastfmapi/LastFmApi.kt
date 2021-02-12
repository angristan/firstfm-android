package fr.esgi.firstfm.lastfmapi

import LastFmApiAlbumGetInfoResponse
import fr.esgi.firstfm.BuildConfig
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

    fun retrieveTrackInfo(
        artist: String,
        title: String,
        callback: Callback<LastFmApiTrackGetInfoResponse>
    ) {
        val call =
            lastFmApiServices?.retrieveTrackInfo(artist, title, BuildConfig.LASTFM_API_TOKEN)
        call?.enqueue(callback)
    }

    fun retrieveAlbumInfo(
        artist: String?,
        album: String?,
        callback: Callback<LastFmApiAlbumGetInfoResponse>
    ) {
        val call =
            lastFmApiServices?.getAlbumInfo(artist, album, BuildConfig.LASTFM_API_TOKEN)
        call?.enqueue(callback)
    }

    fun retrieveArtistInfo(
        artist: String?,
        callback: Callback<LastFmApiArtistGetInfoResponse>
    ) {
        val call =
            lastFmApiServices?.getArtistInfo(artist, BuildConfig.LASTFM_API_TOKEN)
        call?.enqueue(callback)
    }

    fun retrieveArtistTopAlbumsInfo(
        artist: String?,
        callback: Callback<LastFmApiArtistTopAlbumsGetInfoResponse>
    ) {
        val call =
            lastFmApiServices?.getArtistTopAlbumsInfo(artist, BuildConfig.LASTFM_API_TOKEN)
        call?.enqueue(callback)
    }
}