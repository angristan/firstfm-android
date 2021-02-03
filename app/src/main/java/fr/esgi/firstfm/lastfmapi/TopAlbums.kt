package fr.esgi.firstfm.lastfmapi

import com.google.gson.annotations.SerializedName

data class TopAlbums(
        @SerializedName("album") val albums: List<AlbumFromTop>
)
