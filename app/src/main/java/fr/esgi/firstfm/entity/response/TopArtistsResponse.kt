package fr.esgi.firstfm.entity

import fr.esgi.firstfm.entity.model.Artist
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopArtistsResponse(
    @SerialName("topartists")
    val artistsContainer: TopArtistsResponseArtistContainer
)

@Serializable
data class TopArtistsResponseArtistContainer(
    @SerialName("artist")
    val artists: List<Artist>
)