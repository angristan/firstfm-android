import fr.esgi.firstfm.entity.SpotifyImage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpotifyArtist(
    @SerialName("name") val name: String = "",
    @SerialName("id") val id: String = "",
    @SerialName("images") val images: List<SpotifyImage> = listOf()
)