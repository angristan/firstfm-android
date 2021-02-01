package fr.esgi.firstfm.lastfmapi

import com.google.gson.annotations.SerializedName

data class Tag(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)
