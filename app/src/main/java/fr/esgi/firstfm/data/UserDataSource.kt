package fr.esgi.firstfm.data

import fr.esgi.firstfm.api.User
import fr.esgi.firstfm.entity.TopAlbumsResponse
import okhttp3.OkHttpClient


class UserDataSource {
    private val client = OkHttpClient()

    fun getTopAlbums(username: String): Result<TopAlbumsResponse> {
        return User.getTopAlbums(username)
    }

}
