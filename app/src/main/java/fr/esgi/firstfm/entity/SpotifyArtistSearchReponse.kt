package fr.esgi.firstfm.entity

import SpotifyArtist
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpotifyArtistSearchReponse(
    @SerialName("artists")
    val artistsContainer: SpotifyArtistSearchResultsResponse
)

@Serializable
data class SpotifyArtistSearchResultsResponse(
    @SerialName("items")
    val artistsResults: List<SpotifyArtist>
)