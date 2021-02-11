package fr.esgi.firstfm.lastfmapi

import com.google.gson.annotations.SerializedName

open class LastFmApiArtistTopAlbumsGetInfoResponse(
    @SerializedName("topalbums") val albums: TopArtistAlbums
)