package fr.esgi.firstfm.lastfmapi

import com.google.gson.annotations.SerializedName

data class AlbumResponse(
        @SerializedName("artist") val artist: String,
        @SerializedName("name") val name: String,
        @SerializedName("mbid") val mbId: String,
        @SerializedName("image") val images: List<Image>,
        @SerializedName("tracks") val tracks: Tracks,
        @SerializedName("tags") val tags: TopTags,
        @SerializedName("listeners") val listeners: String,
        @SerializedName("playcount") val playCount: String
)
