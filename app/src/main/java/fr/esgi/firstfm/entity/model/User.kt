package fr.esgi.firstfm.entity.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("name") val name: String = "",
    @SerialName("playcount") val playcount: Long = 0,
    @SerialName("subscriber") val subscriber: String = "",
    @SerialName("image") val images: List<Image> = listOf(),
)