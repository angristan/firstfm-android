package fr.esgi.firstfm.data

import android.util.Log
import fr.esgi.firstfm.data.model.LoggedInUser
import okhttp3.*
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.security.MessageDigest

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

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
        var toSign = ""


        for (item in params) {
            Log.v("wesh", item)
            toSign += item
        }

        toSign += "e4e1eb5bf14d2418f51ed6ea6ae5d91a"

        return md5(toSign)

    }

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication

            var APIKey = "d404c94c63e190519d70002332f09509"
            var method = "auth.getMobileSession"
//            var APISig = "2872cff6f3c173a96b9af991ed487fbf"
            var APISig = buildSignature(
                "api_key",
                APIKey,
                "method",
                method,
                "password",
                password,
                "username",
                username
            )

            if (APISig != null) {
                val okHttpClient = OkHttpClient()
                val body =
                    FormBody.Builder().addEncoded("api_key", APIKey).addEncoded("method", method)
                        .addEncoded("username", username).addEncoded("password", password)
                        .addEncoded("api_sig", APISig)
                val request = Request.Builder()
                    .method("POST", body.build())
                    .url("https://ws.audioscrobbler.com/2.0/?format=json")
                    .build()
                okHttpClient.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Log.v("wesh", e.toString())
                    }

                    override fun onResponse(call: Call, response: Response) {
                        Log.v("wesh", response.toString())
                        response.body?.string()?.let { Log.v("wesh", it) }
                    }
                })
            }


            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")
            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}
