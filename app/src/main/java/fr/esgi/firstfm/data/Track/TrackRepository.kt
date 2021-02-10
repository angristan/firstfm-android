package fr.esgi.firstfm.data.Artist

import androidx.appcompat.app.AppCompatActivity
import fr.esgi.firstfm.data.Result
import fr.esgi.firstfm.data.Track.TrackDataSource
import fr.esgi.firstfm.entity.model.SpotifyImage

class TrackRepository(private val dataSource: TrackDataSource) {
    fun getImage(
        activity: AppCompatActivity,
        track: String,
        artist: String
    ): Result<SpotifyImage> {
        return dataSource.getImage(activity, track, artist)
    }
}