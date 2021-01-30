package fr.esgi.firstfm.data.Artist

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import fr.esgi.firstfm.data.Result
import fr.esgi.firstfm.entity.SpotifyArtistSearchReponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException
import java.net.URLEncoder

class ArtistDataSource {

    fun renewSpotifyToken(activity: AppCompatActivity) {

        val basicToken =
            "ZmQxYTVkZDBlMzIzNDgyZGI3OGE5MWUyOTQ0NmFjNWE6ZWI5NTFmOWZiZmE0NDczM2I3ZTkxYjI4NTE3YTIyNDE="
        val body =
            FormBody.Builder()
                .addEncoded("grant_type", "client_credentials")


        val request = Request.Builder()
            .method("POST", body.build())
            .url("https://accounts.spotify.com/api/token")
            .addHeader("Authorization", "Basic $basicToken")
            .build()

        OkHttpClient().newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val token = JSONObject(response.body!!.string()).getString("access_token")

            val prefs: SharedPreferences =
                activity.getSharedPreferences("firstfm", Context.MODE_PRIVATE)

            val spotifyToken = JSONObject()
            spotifyToken.put(
                "token",
                token
            )
            spotifyToken.put("expires_at", System.currentTimeMillis() + 3600)
            val myEdit = prefs.edit()

            myEdit.putString("spotifyToken", spotifyToken.toString())

            myEdit.apply()
        }


    }

    fun getSpotifyToken(activity: AppCompatActivity): String {
        val prefs: SharedPreferences =
            activity.getSharedPreferences("firstfm", Context.MODE_PRIVATE)

        val intialSpotifyToken = prefs.getString("spotifyToken", "")

        if (intialSpotifyToken == null || intialSpotifyToken == "") {
            renewSpotifyToken(activity)
        } else {
            val expiresAt = JSONObject(intialSpotifyToken).getLong("expires_at")

            if (System.currentTimeMillis() + 30 > expiresAt) {
                renewSpotifyToken(activity)
            }
        }
        val spotifyToken = prefs.getString("spotifyToken", "")
        if (spotifyToken == null || spotifyToken == "") {
            throw IOException("Can't get Spotify token")
        }

        return JSONObject(spotifyToken).getString("token")
    }

    fun getImage(
        activity: AppCompatActivity,
        artist: String
    ): Result<SpotifyArtistSearchReponse> {
        try {
            val spotifyToken = getSpotifyToken(activity)

            val encodedArtist = URLEncoder.encode(artist, "utf-8")
            val url = "https://api.spotify.com/v1/search?q=${encodedArtist}&type=artist"

            val request = Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer $spotifyToken")
                .build()

            OkHttpClient().newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                val format = Json { ignoreUnknownKeys = true }

                val artists =
                    format.decodeFromString<SpotifyArtistSearchReponse>(response.body!!.string())

                return Result.Success(artists)
            }

        } catch (e: Exception) {
            return Result.Error(IOException("Error fetching artist: " + e.message, e))
        }
    }
}