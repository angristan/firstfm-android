package fr.esgi.firstfm.objects

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Album(
    @SerialName("name") val name: String = "",
    @SerialName("mbid") val mbid: String = "",
    @SerialName("playcount") val playcount: Int = 0,
    @SerialName("url") val url: String = "",
    @SerialName("artist") val artist: Artist = Artist(),
    @SerialName("image") val image: List<AlbumImage> = listOf()
)

@Serializable
data class AlbumImage(
    @SerialName("size") val size: String = "",
    @SerialName("#text") val url: String = ""
)