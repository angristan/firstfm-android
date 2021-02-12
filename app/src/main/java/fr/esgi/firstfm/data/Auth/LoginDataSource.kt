package fr.esgi.firstfm.data.Auth

import fr.esgi.firstfm.BuildConfig
import fr.esgi.firstfm.data.Result
import fr.esgi.firstfm.entity.LoggedInUser
import fr.esgi.firstfm.entity.LoggedInUserSession
import fr.esgi.firstfm.util.BuildApiSignature
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */

class LoginDataSource {
    fun login(username: String, password: String): Result<LoggedInUser> {

        try {
            val method = "auth.getMobileSession"
            val signature = BuildApiSignature(
                "method",
                method,
                "username",
                username,
                "password",
                password,
            ) ?: throw Exception("Could not build API signature")

            val body =
                FormBody.Builder()
                    .addEncoded("api_key", BuildConfig.LASTFM_API_TOKEN)
                    .addEncoded("method", method)
                    .addEncoded("username", username)
                    .addEncoded("password", password)
                    .addEncoded("api_sig", signature)

            val request = Request.Builder()
                .method("POST", body.build())
                .url("https://ws.audioscrobbler.com/2.0/?format=json")
                .build()

            OkHttpClient().newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                val format = Json { ignoreUnknownKeys = true }

                val user =
                    format.decodeFromString<LoggedInUserSession>(response.body!!.string())

                return Result.Success(user.user)
            }

        } catch (e: Throwable) {
            return Result.Error(
                IOException(
                    "Error logging in: " + e.message,
                    e
                )
            )
        }
    }
}
