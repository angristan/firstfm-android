package fr.esgi.firstfm.data.User

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import fr.esgi.firstfm.data.Result
import fr.esgi.firstfm.entity.TopAlbumsResponse
import fr.esgi.firstfm.entity.TopArtistsResponse
import fr.esgi.firstfm.entity.TopTracksResponse
import fr.esgi.firstfm.entity.response.UserInfoResponse

class UserRepository(val dataSource: UserDataSource) {
    fun getTopAlbums(
        activity: AppCompatActivity,
    ): Result<TopAlbumsResponse> {
        val sharedPreferences: SharedPreferences =
            activity.getSharedPreferences("firstfm", MODE_PRIVATE)

        val username = sharedPreferences.getString("username", "")
        return dataSource.getTopAlbums(username!!)
    }

    fun getTopArtists(
        activity: AppCompatActivity,
    ): Result<TopArtistsResponse> {
        val sharedPreferences: SharedPreferences =
            activity.getSharedPreferences("firstfm", MODE_PRIVATE)

        val username = sharedPreferences.getString("username", "")
        return dataSource.getTopArtists(username!!)
    }


    fun getTopTracks(
        activity: AppCompatActivity,
    ): Result<TopTracksResponse> {
        val sharedPreferences: SharedPreferences =
            activity.getSharedPreferences("firstfm", MODE_PRIVATE)

        val username = sharedPreferences.getString("username", "")
        return dataSource.getTopTracks(username!!)
    }

    fun getInfo(
        activity: AppCompatActivity,
    ): Result<UserInfoResponse> {
        val sharedPreferences: SharedPreferences =
            activity.getSharedPreferences("firstfm", MODE_PRIVATE)

        val username = sharedPreferences.getString("username", "")
        return dataSource.getInfo(username!!)
    }
}