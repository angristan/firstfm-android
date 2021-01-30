package fr.esgi.firstfm.objects

import Image
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Artist(
    @SerialName("name") val name: String = "",
    @SerialName("mbid") val mbid: String = "",
    @SerialName("url") val url: String = "",
    @SerialName("playcount") val playcount: Int = 0,
    @SerialName("image") val image: List<Image> = listOf()
)