package fr.esgi.firstfm.entity

import fr.esgi.firstfm.entity.model.SpotifyArtist
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpotifyArtistSearchResponse(
    @SerialName("artists")
    val artistsContainer: SpotifyArtistSearchResultsResponse
)

@Serializable
data class SpotifyArtistSearchResultsResponse(
    @SerialName("items")
    val artistsResults: List<SpotifyArtist>
)