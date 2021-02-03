import com.google.gson.annotations.SerializedName
import fr.esgi.firstfm.lastfmapi.AlbumResponse

open class LastFmApiAlbumGetInfoResponse(@SerializedName("album") val album: AlbumResponse)
