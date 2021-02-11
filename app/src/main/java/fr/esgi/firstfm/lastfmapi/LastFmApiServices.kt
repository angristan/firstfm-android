package fr.esgi.firstfm.lastfmapi

import LastFmApiAlbumGetInfoResponse
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

    @GET("?method=album.getInfo&format=json")
    fun getAlbumInfo(
        @Query("mbid") mbid: String?,
        @Query("artist") artist: String?,
        @Query("album") album: String?,
        @Query("api_key") apiKey: String
    ): Call<LastFmApiAlbumGetInfoResponse>

    @GET("?method=artist.getInfo&format=json")
    fun getArtistInfo(
        @Query("mbid") mbid: String?,
        @Query("artist") artist: String?,
        @Query("api_key") apiKey: String
    ): Call<LastFmApiArtistGetInfoResponse>

    @GET("?method=artist.gettopalbums&format=json&limit=15")
    fun getArtistTopAlbumsInfo(
        @Query("mbid") mbid: String?,
        @Query("artist") artist: String?,
        @Query("api_key") apiKey: String
    ): Call<LastFmApiArtistTopAlbumsGetInfoResponse>
}