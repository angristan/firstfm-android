package fr.esgi.firstfm.data

import fr.esgi.firstfm.api.Auth
import fr.esgi.firstfm.entity.LoggedInUser
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */

val format = Json { ignoreUnknownKeys = true }

const val API_SECRET = "e4e1eb5bf14d2418f51ed6ea6ae5d91a"
var API_KEY = "d404c94c63e190519d70002332f09509"


class LoginDataSource {
    private val client = OkHttpClient()

    fun login(username: String, password: String): Result<LoggedInUser> {
        return Auth.getMobileSession(username, password)
    }

    fun logout() {
        // TODO: revoke authentication
    }
}
