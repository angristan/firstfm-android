import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Track(
    @SerialName("name") val name: String = "",
    @SerialName("mbid") val mbid: String = "",
    @SerialName("url") val url: String = "",
    @SerialName("playcount") val playcount: Int = 0,
    @SerialName("image") val image: List<Image> = listOf()
)