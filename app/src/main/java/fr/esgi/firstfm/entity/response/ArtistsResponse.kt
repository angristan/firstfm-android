package fr.esgi.firstfm.entity.response

import fr.esgi.firstfm.entity.TopArtistsResponseArtistContainer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtistsResponse(
    @SerialName("artists")
    val artistsContainer: TopArtistsResponseArtistContainer
)
