package fr.esgi.firstfm.data

import android.util.Log
import fr.esgi.firstfm.data.model.LoggedInUser
import okhttp3.*
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication

            var APIKey = "d404c94c63e190519d70002332f09509"
            var method = "auth.getMobileSession"
            var APISig = "2872cff6f3c173a96b9af991ed487fbf"


            val okHttpClient = OkHttpClient()
            val body = FormBody.Builder().addEncoded("api_key", APIKey).addEncoded("method", method)
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
