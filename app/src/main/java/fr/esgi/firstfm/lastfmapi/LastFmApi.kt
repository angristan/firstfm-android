package fr.esgi.firstfm.lastfmapi

import LastFmApiAlbumGetInfoResponse
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
            lastFmApiServices?.retrieveTrackInfo(artist, title, "b61ee97cfc7ed80dad34909f0c980b8e")
        call?.enqueue(callback)
    }

    fun retrieveAlbumInfo(
        mbId: String?,
        artist: String?,
        album: String?,
        callback: Callback<LastFmApiAlbumGetInfoResponse>
    ) {
        val call =
            lastFmApiServices?.getAlbumInfo(mbId, artist, album, "b61ee97cfc7ed80dad34909f0c980b8e")
        call?.enqueue(callback)
    }

    fun retrieveArtistInfoByMbId(
        mbId: String?,
        artist: String?,
        callback: Callback<ArtistResponse>
    ) {
        val call =
            lastFmApiServices?.getArtistInfo(mbId, artist, "b61ee97cfc7ed80dad34909f0c980b8e")
        call?.enqueue(callback)
    }

    fun retrieveArtistTopAlbumsInfoByMbId(
        mbId: String?,
        artist: String?,
        callback: Callback<LastFmApiArtistTopAlbumsGetInfoResponse>
    ) {
        val call = lastFmApiServices?.getArtistTopAlbumsInfo(
            mbId,
            artist,
            "b61ee97cfc7ed80dad34909f0c980b8e"
        )
        call?.enqueue(callback)
    }
}