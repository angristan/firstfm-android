package fr.esgi.firstfm.entity.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpotifyArtist(
    @SerialName("name")
    @SerializedName("name")
    val name: String = "",
    @SerialName("id")
    @SerializedName("id")
    val id: String = "",
    @SerialName("images")
    @SerializedName("images")
    val images: List<SpotifyImage> = listOf()
)