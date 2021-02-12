package fr.esgi.firstfm.data.User

import fr.esgi.firstfm.BuildConfig
import fr.esgi.firstfm.data.Result
import fr.esgi.firstfm.entity.TopAlbumsResponse
import fr.esgi.firstfm.entity.TopArtistsResponse
import fr.esgi.firstfm.entity.TopTracksResponse
import fr.esgi.firstfm.entity.response.UserInfoResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException


class UserDataSource {
    fun getTopAlbums(username: String?): Result<TopAlbumsResponse> {
        if (username == null) {
            return Result.Error(IOException("Error getting top albums"))
        }
        try {
            val method = "user.getTopAlbums"

            val body =
                FormBody.Builder()
                    .addEncoded("api_key", BuildConfig.LASTFM_API_TOKEN)
                    .addEncoded("method", method)
                    .addEncoded("username", username)
                    .addEncoded("period", "1month")
                    .addEncoded("limit", "5")

            val request = Request.Builder()
                .method("POST", body.build())
                .url("https://ws.audioscrobbler.com/2.0/?format=json")
                .build()

            OkHttpClient().newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                val format = Json { ignoreUnknownKeys = true }

                val topAlbums =
                    format.decodeFromString<TopAlbumsResponse>(response.body!!.string())

                return Result.Success(topAlbums)
            }

        } catch (e: Throwable) {
            return Result.Error(IOException("Error getting top albums: " + e.message, e))
        }
    }

    fun getTopArtists(username: String?): Result<TopArtistsResponse> {
        if (username == null) {
            return Result.Error(IOException("Error getting top albums"))
        }
        try {
            val method = "user.getTopArtists"
            val body =
                FormBody.Builder()
                    .addEncoded("api_key", BuildConfig.LASTFM_API_TOKEN)
                    .addEncoded("method", method)
                    .addEncoded("username", username)
                    .addEncoded("period", "1month")
                    .addEncoded("limit", "5")

            val request = Request.Builder()
                .method("POST", body.build())
                .url("https://ws.audioscrobbler.com/2.0/?format=json")
                .build()

            OkHttpClient().newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                val format = Json { ignoreUnknownKeys = true }

                val topArtists =
                    format.decodeFromString<TopArtistsResponse>(response.body!!.string())

                return Result.Success(topArtists)
            }

        } catch (e: Throwable) {
            return Result.Error(IOException("Error getting top artists: " + e.message, e))
        }
    }

    fun getTopTracks(username: String?): Result<TopTracksResponse> {
        if (username == null) {
            return Result.Error(IOException("Error getting top albums"))
        }
        try {
            val method = "user.getTopTracks"
            val body =
                FormBody.Builder()
                    .addEncoded("api_key", BuildConfig.LASTFM_API_TOKEN)
                    .addEncoded("method", method)
                    .addEncoded("username", username)
                    .addEncoded("period", "1month")
                    .addEncoded("limit", "5")

            val request = Request.Builder()
                .method("POST", body.build())
                .url("https://ws.audioscrobbler.com/2.0/?format=json")
                .build()

            OkHttpClient().newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                val format = Json { ignoreUnknownKeys = true }

                val topTracks =
                    format.decodeFromString<TopTracksResponse>(response.body!!.string())

                return Result.Success(topTracks)
            }

        } catch (e: Throwable) {
            return Result.Error(IOException("Error getting top tracks: " + e.message, e))
        }
    }

    fun getInfo(username: String?): Result<UserInfoResponse> {
        if (username == null) {
            return Result.Error(IOException("Error getting top albums"))
        }
        try {
            val method = "user.getInfo"
            val body =
                FormBody.Builder()
                    .addEncoded("api_key", BuildConfig.LASTFM_API_TOKEN)
                    .addEncoded("method", method)
                    .addEncoded("username", username)

            val request = Request.Builder()
                .method("POST", body.build())
                .url("https://ws.audioscrobbler.com/2.0/?format=json")
                .build()

            OkHttpClient().newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                val format = Json { ignoreUnknownKeys = true }

                val userResponse =
                    format.decodeFromString<UserInfoResponse>(response.body!!.string())

                return Result.Success(userResponse)
            }

        } catch (e: Throwable) {
            return Result.Error(IOException("Error getting top tracks: " + e.message, e))
        }
    }

}
