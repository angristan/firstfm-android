package fr.esgi.firstfm.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopTracksResponse(
    @SerialName("toptracks")
    val tracksContainer: TopTracksResponseTrackContainer
)

@Serializable
data class TopTracksResponseTrackContainer(
    @SerialName("track")
    val tracks: List<Track>
)