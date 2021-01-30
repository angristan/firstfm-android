package fr.esgi.firstfm.data.User

import fr.esgi.firstfm.data.Result
import fr.esgi.firstfm.entity.TopAlbumsResponse
import fr.esgi.firstfm.entity.TopArtistsResponse
import fr.esgi.firstfm.util.BuildApiSignature
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException


class UserDataSource {
    fun getTopAlbums(username: String): Result<TopAlbumsResponse> {
        val API_KEY = "d404c94c63e190519d70002332f09509"

        try {
            val method = "user.getTopAlbums"
            val signature = BuildApiSignature(
                "method",
                method,
                "username",
                username,
                "limit",
                "5"
            ) ?: throw Exception("Could not build API signature")

            val body =
                FormBody.Builder()
                    .addEncoded("api_key", API_KEY)
                    .addEncoded("method", method)
                    .addEncoded("username", username)
                    .addEncoded("limit", "5")
                    .addEncoded("api_sig", signature)

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
            return Result.Error(IOException("Error logging in: " + e.message, e))
        }
    }

    fun getTopArtists(username: String): Result<TopArtistsResponse> {
        val API_KEY = "d404c94c63e190519d70002332f09509"

        try {
            val method = "user.getTopArtists"
            val signature = BuildApiSignature(
                "method",
                method,
                "username",
                username,
                "limit",
                "5"
            ) ?: throw Exception("Could not build API signature")

            val body =
                FormBody.Builder()
                    .addEncoded("api_key", API_KEY)
                    .addEncoded("method", method)
                    .addEncoded("username", username)
                    .addEncoded("limit", "5")
                    .addEncoded("api_sig", signature)

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
            return Result.Error(IOException("Error logging in: " + e.message, e))
        }
    }

}
