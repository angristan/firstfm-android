package fr.esgi.firstfm.entity

import SpotifyTrack
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpotifyTrackSearchResponse(
    @SerialName("tracks")
    val tracksContainer: SpotifyTrackSearchResultsContainerResponse
)

@Serializable
data class SpotifyTrackSearchResultsContainerResponse(
    @SerialName("items")
    val tracksResults: List<SpotifyTrackSearchResultResponse>
)

@Serializable
data class SpotifyTrackSearchResultResponse(
    @SerialName("album")
    val track: SpotifyTrack
)