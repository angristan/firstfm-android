package fr.esgi.firstfm.entity.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Track(
    @SerialName("name") val name: String = "",
    @SerialName("mbid") val mbid: String = "",
    @SerialName("url") val url: String = "",
    @SerialName("playcount") val playcount: Int = 0,
    @SerialName("artist") val artist: Artist = Artist(),

    @SerialName("image") val image: List<Image> = listOf(),
    var spotifyImage: SpotifyImage = SpotifyImage(),
)