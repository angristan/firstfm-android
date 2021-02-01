package fr.esgi.firstfm.lastfmapi

import com.google.gson.annotations.SerializedName

data class Album(
    @SerializedName("artist") val artist: String,
    @SerializedName("title") val title: String,
    @SerializedName("image") val images: List<Image>
)
