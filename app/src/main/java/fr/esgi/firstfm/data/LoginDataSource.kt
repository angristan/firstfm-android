package fr.esgi.firstfm.data

import android.util.Log
import fr.esgi.firstfm.data.model.LoggedInUser
import fr.esgi.firstfm.data.model.LoggedInUserSession
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.security.MessageDigest

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */

val format = Json { ignoreUnknownKeys = true }

val API_SECRET = "e4e1eb5bf14d2418f51ed6ea6ae5d91a"
var API_KEY = "d404c94c63e190519d70002332f09509"


class LoginDataSource {
    private val client = OkHttpClient()

    fun md5(s: String): String? {
        try {
            val bytes: ByteArray =
                MessageDigest.getInstance("MD5").digest(s.toByteArray(charset("UTF-8")))
            val b = StringBuilder(32)
            for (aByte in bytes) {
                val hex = Integer.toHexString(aByte.toInt() and 0xFF)
                if (hex.length == 1) b.append('0')
                b.append(hex)
            }
            return b.toString()
        } catch (e: UnsupportedEncodingException) {
            // utf-8 always available
        }
        return null
    }

    fun buildSignature(vararg params: String): String? {

        // Api key is always part of the params
        val paramsMap = mutableMapOf("api_key" to API_KEY)

        // transform vararg to map
        for (i in params.indices step 2) {
            paramsMap[params[i]] = params[i + 1]
        }

        // sort map by keys
        // this is because the signature has to be made with params in alphabetical order
        // for the last.fm API
        val sortedParams = paramsMap.toSortedMap()

        // init our to-be-signed string
        var toSign = ""

        // add params
        for (key in sortedParams.keys) {
            toSign += key
            toSign += sortedParams[key]
        }

        // end with secret
        toSign += API_SECRET

        Log.v("wesh", toSign)

        // return signature :)
        return md5(toSign)

    }

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {

            Log.v("wesh", "coucou")


            val method = "auth.getMobileSession"
            val signature = buildSignature(
                "method",
                method,
                "username",
                username,
                "password",
                password,
            )

            if (signature != null) {
                val body =
                    FormBody.Builder().addEncoded("api_key", API_KEY).addEncoded("method", method)
                        .addEncoded("username", username).addEncoded("password", password)
                        .addEncoded("api_sig", signature)

                val request = Request.Builder()
                    .method("POST", body.build())
                    .url("https://ws.audioscrobbler.com/2.0/?format=json")
                    .build()


                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val user =
                        format.decodeFromString<LoggedInUserSession>(response.body!!.string())

                    return Result.Success(user.user)
                }
            }

        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in: " + e.message, e))
        }
        return Result.Error(Exception("Error logging in"))
    }

    fun logout() {
        // TODO: revoke authentication
    }
}
