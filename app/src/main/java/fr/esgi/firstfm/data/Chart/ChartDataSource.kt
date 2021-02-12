package fr.esgi.firstfm.data.User

import TracksResponse
import fr.esgi.firstfm.BuildConfig
import fr.esgi.firstfm.data.Result
import fr.esgi.firstfm.entity.response.ArtistsResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class ChartDataSource {

    fun getTopArtists(): Result<ArtistsResponse> {
        try {
            val method = "chart.getTopArtists"
            val body =
                FormBody.Builder()
                    .addEncoded("api_key", BuildConfig.LASTFM_API_TOKEN)
                    .addEncoded("method", method)
                    .addEncoded("limit", "5")

            val request = Request.Builder()
                .method("POST", body.build())
                .url("https://ws.audioscrobbler.com/2.0/?format=json")
                .build()

            OkHttpClient().newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                val format = Json { ignoreUnknownKeys = true }

                val topArtists =
                    format.decodeFromString<ArtistsResponse>(response.body!!.string())

                return Result.Success(topArtists)
            }

        } catch (e: Throwable) {
            val msg = e.stackTrace
            return Result.Error(IOException("Error getting top artists: " + e.message, e))
        }
    }

    fun getTopTracks(): Result<TracksResponse> {
        try {
            val method = "chart.getTopTracks"
            val body =
                FormBody.Builder()
                    .addEncoded("api_key", BuildConfig.LASTFM_API_TOKEN)
                    .addEncoded("method", method)
                    .addEncoded("limit", "5")

            val request = Request.Builder()
                .method("POST", body.build())
                .url("https://ws.audioscrobbler.com/2.0/?format=json")
                .build()

            OkHttpClient().newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                val format = Json { ignoreUnknownKeys = true }

                val topTracks =
                    format.decodeFromString<TracksResponse>(response.body!!.string())

                return Result.Success(topTracks)
            }

        } catch (e: Throwable) {
            return Result.Error(IOException("Error getting top tracks: " + e.message, e))
        }
    }
}
