package fr.esgi.firstfm.data.Artist

import androidx.appcompat.app.AppCompatActivity
import fr.esgi.firstfm.data.Result
import fr.esgi.firstfm.data.Spotify
import fr.esgi.firstfm.entity.SpotifyArtistSearchReponse
import fr.esgi.firstfm.entity.model.SpotifyImage
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.net.URLEncoder

class ArtistDataSource {


    fun getImage(
        activity: AppCompatActivity,
        artist: String
    ): Result<SpotifyImage> {
        try {
            val spotifyToken = Spotify().getSpotifyToken(activity)

            val encodedArtist = URLEncoder.encode(artist, "utf-8")
            val url = "https://api.spotify.com/v1/search?q=${encodedArtist}&type=artist"

            val request = Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer $spotifyToken")
                .build()

            OkHttpClient().newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                val format = Json { ignoreUnknownKeys = true }

                val artists =
                    format.decodeFromString<SpotifyArtistSearchReponse>(response.body!!.string())

                return Result.Success(artists.artistsContainer.artistsResults[0].images[0])
            }

        } catch (e: Exception) {
            return Result.Error(IOException("Error fetching artist: " + e.message, e))
        }
    }
}