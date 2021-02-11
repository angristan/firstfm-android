package fr.esgi.firstfm.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fr.esgi.firstfm.data.Artist.ArtistDataSource
import fr.esgi.firstfm.data.Artist.ArtistRepository
import fr.esgi.firstfm.data.Artist.TrackRepository
import fr.esgi.firstfm.data.Track.TrackDataSource
import fr.esgi.firstfm.data.User.UserDataSource
import fr.esgi.firstfm.data.User.UserRepository
import fr.esgi.firstfm.profile.ProfileViewModel

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class ProfileViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(
                userRepository = UserRepository(
                    dataSource = UserDataSource()
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