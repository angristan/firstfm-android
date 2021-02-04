package fr.esgi.firstfm.lastfmapi

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("#text") val url: String,
    @SerializedName("size") val size: String
)