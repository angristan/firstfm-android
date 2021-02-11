package fr.esgi.firstfm.lastfmapi

import com.google.gson.annotations.SerializedName

data class TopArtistAlbums(
    @SerializedName("album") val albums: List<AlbumFromTop>
)
