package fr.esgi.firstfm.data

import fr.esgi.firstfm.api.User
import fr.esgi.firstfm.entity.TopAlbumsResponse


class UserDataSource {
    fun getTopAlbums(username: String): Result<TopAlbumsResponse> {
        return User.getTopAlbums(username)
    }

}
