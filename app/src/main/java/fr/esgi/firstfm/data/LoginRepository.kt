package fr.esgi.firstfm.data

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import fr.esgi.firstfm.api.Auth
import fr.esgi.firstfm.entity.LoggedInUser


/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    fun login(
        activity: AppCompatActivity,
        username: String,
        password: String
    ): Result<LoggedInUser> {
        // handle login
        val result = Auth.getMobileSession(username, password)

        if (result is Result.Success) {
            setLoggedInUser(activity, result.data)
        }

        return result
    }

    private fun setLoggedInUser(activity: AppCompatActivity, loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore

        val sharedPreferences: SharedPreferences =
            activity.getSharedPreferences("firstfm", MODE_PRIVATE)

        val myEdit = sharedPreferences.edit()

        myEdit.putString("token", loggedInUser.token)
        myEdit.putString("username", loggedInUser.userId)

        myEdit.apply()
    }
}