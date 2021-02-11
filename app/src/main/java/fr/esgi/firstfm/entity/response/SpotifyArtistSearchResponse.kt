package fr.esgi.firstfm.entity

import com.google.gson.annotations.SerializedName
import fr.esgi.firstfm.entity.model.SpotifyArtist
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpotifyArtistSearchResponse(
    @SerialName("artists")
    @SerializedName("artists")
    val artistsContainer: SpotifyArtistSearchResultsResponse
)

@Serializable
data class SpotifyArtistSearchResultsResponse(
    @SerialName("items")
    @SerializedName("items")
    val artistsResults: List<SpotifyArtist>
)