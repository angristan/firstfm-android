package fr.esgi.firstfm.data.User

import TracksResponse
import androidx.appcompat.app.AppCompatActivity
import fr.esgi.firstfm.data.Result
import fr.esgi.firstfm.entity.response.ArtistsResponse

class ChartRepository(private val dataSource: ChartDataSource) {
    fun getTopArtists(
        activity: AppCompatActivity,
    ): Result<ArtistsResponse> {
        return dataSource.getTopArtists()
    }

    fun getTopTracks(
        activity: AppCompatActivity,
    ): Result<TracksResponse> {
        return dataSource.getTopTracks()
    }
}