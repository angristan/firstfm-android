package fr.esgi.firstfm.entity.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpotifyTrack(
    @SerialName("name") val name: String = "",
    @SerialName("id") val id: String = "",
    @SerialName("images") val images: List<SpotifyImage> = listOf()
)