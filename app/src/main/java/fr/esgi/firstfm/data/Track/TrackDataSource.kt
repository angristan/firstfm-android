package fr.esgi.firstfm.data.Track

import androidx.appcompat.app.AppCompatActivity
import fr.esgi.firstfm.data.Result
import fr.esgi.firstfm.data.Spotify
import fr.esgi.firstfm.entity.SpotifyTrackSearchResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.net.URLEncoder

class TrackDataSource {
    fun getImage(
        activity: AppCompatActivity,
        track: String,
        artist: String
    ): Result<SpotifyTrackSearchResponse> {
        try {
            val spotifyToken = Spotify().getSpotifyToken(activity)

            val encodedSearch = URLEncoder.encode("$artist - $track ", "utf-8")
            val url = "https://api.spotify.com/v1/search?q=${encodedSearch}&type=track"

            val request = Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer $spotifyToken")
                .build()

            OkHttpClient().newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                val format = Json { ignoreUnknownKeys = true }

                val tracks =
                    format.decodeFromString<SpotifyTrackSearchResponse>(response.body!!.string())

                return Result.Success(tracks)
            }

        } catch (e: Exception) {
            return Result.Error(IOException("Error fetching artist: " + e.message, e))
        }
    }
}