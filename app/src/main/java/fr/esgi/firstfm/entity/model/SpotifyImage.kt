package fr.esgi.firstfm.entity.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpotifyImage(
    @SerialName("url") val url: String = "",
    @SerialName("height") val height: Int = 0,
    @SerialName("width") val width: Int = 0,
)