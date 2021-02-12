import fr.esgi.firstfm.entity.TopTracksResponseTrackContainer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TracksResponse(
    @SerialName("tracks")
    val tracksContainer: TopTracksResponseTrackContainer
)
