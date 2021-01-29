package fr.esgi.firstfm.entity

import fr.esgi.firstfm.objects.Album
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopAlbumsResponse(
    @SerialName("topalbums")
    val albumsContainer: TopAlbumsResponseAlbum
)

@Serializable
data class TopAlbumsResponseAlbum(
    @SerialName("album")
    val albums: List<Album>
)