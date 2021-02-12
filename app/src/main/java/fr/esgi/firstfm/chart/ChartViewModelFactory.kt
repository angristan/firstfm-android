package fr.esgi.firstfm.chart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fr.esgi.firstfm.data.Artist.ArtistDataSource
import fr.esgi.firstfm.data.Artist.ArtistRepository
import fr.esgi.firstfm.data.Artist.TrackRepository
import fr.esgi.firstfm.data.Track.TrackDataSource
import fr.esgi.firstfm.data.User.ChartDataSource
import fr.esgi.firstfm.data.User.ChartRepository

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class ChartViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChartViewModel::class.java)) {
            return ChartViewModel(
                chartRepository = ChartRepository(
                    dataSource = ChartDataSource()
                ),
                artistRepository = ArtistRepository(
                    dataSource = ArtistDataSource()
                ),
                trackRepository = TrackRepository(
                    dataSource = TrackDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}