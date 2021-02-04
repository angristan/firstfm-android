package fr.esgi.firstfm.data.Artist

import androidx.appcompat.app.AppCompatActivity
import fr.esgi.firstfm.data.Result
import fr.esgi.firstfm.entity.SpotifyArtistSearchReponse

class ArtistRepository(private val dataSource: ArtistDataSource) {
    fun getImage(
        activity: AppCompatActivity,
        artist: String
    ): Result<SpotifyArtistSearchReponse> {
        return dataSource.getImage(activity, artist)
    }
}