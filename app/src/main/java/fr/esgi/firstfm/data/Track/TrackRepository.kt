package fr.esgi.firstfm.data.Artist

import androidx.appcompat.app.AppCompatActivity
import fr.esgi.firstfm.data.Result
import fr.esgi.firstfm.data.Track.TrackDataSource
import fr.esgi.firstfm.entity.SpotifyTrackSearchResponse

class TrackRepository(private val dataSource: TrackDataSource) {
    fun getImage(
        activity: AppCompatActivity,
        track: String,
        artist: String
    ): Result<SpotifyTrackSearchResponse> {
        return dataSource.getImage(activity, track, artist)
    }
}