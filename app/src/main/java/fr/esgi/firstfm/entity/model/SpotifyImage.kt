package fr.esgi.firstfm.entity.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpotifyImage(
    @SerialName("url")
    @SerializedName("url")
    val url: String = "",
    @SerialName("height")
    @SerializedName("height")
    val height: Int = 0,
    @SerialName("width")
    @SerializedName("width")
    val width: Int = 0,
)