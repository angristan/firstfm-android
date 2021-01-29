package fr.esgi.firstfm.api

import android.util.Log
import fr.esgi.firstfm.data.Result
import fr.esgi.firstfm.entity.TopAlbumsResponse
import fr.esgi.firstfm.util.BuildApiSignature
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class User {
    companion object {

        fun getTopAlbums(username: String): Result<TopAlbumsResponse> {
            val API_KEY = "d404c94c63e190519d70002332f09509"

            try {
                val method = "user.getTopAlbums"
                val signature = BuildApiSignature(
                    "method",
                    method,
                    "username",
                    username,
                ) ?: throw Exception("Could not build API signature")

                val body =
                    FormBody.Builder()
                        .addEncoded("api_key", API_KEY)
                        .addEncoded("method", method)
                        .addEncoded("username", username)
                        .addEncoded("api_sig", signature)

                val request = Request.Builder()
                    .method("POST", body.build())
                    .url("https://ws.audioscrobbler.com/2.0/?format=json")
                    .build()

                OkHttpClient().newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    val format = Json { ignoreUnknownKeys = true }

//                    Log.v("wesh", response.body!!.string())

                    val topAlbums =
                        format.decodeFromString<TopAlbumsResponse>(response.body!!.string())

                    Log.v("wesh", topAlbums.toString())



                    return Result.Success(topAlbums)
                }

            } catch (e: Throwable) {
                return Result.Error(IOException("Error logging in: " + e.message, e))
            }
        }
    }
}