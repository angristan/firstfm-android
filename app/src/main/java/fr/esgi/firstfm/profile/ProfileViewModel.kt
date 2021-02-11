package fr.esgi.firstfm.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.esgi.firstfm.ProfileActivity
import fr.esgi.firstfm.R
import fr.esgi.firstfm.data.Artist.ArtistRepository
import fr.esgi.firstfm.data.Artist.TrackRepository
import fr.esgi.firstfm.data.Result
import fr.esgi.firstfm.data.User.UserRepository
import fr.esgi.firstfm.entity.result.TopAlbumsResult
import fr.esgi.firstfm.entity.result.TopArtistsResult
import fr.esgi.firstfm.entity.result.TopTracksResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(
    private val userRepository: UserRepository,
    private val artistRepository: ArtistRepository,
    private val trackRepository: TrackRepository,
) : ViewModel() {

    private val _topAlbumResult = MutableLiveData<TopAlbumsResult>()
    val topAlbumResult: LiveData<TopAlbumsResult> = _topAlbumResult
    private val _topArtistsResult = MutableLiveData<TopArtistsResult>()
    val topArtistsResult: LiveData<TopArtistsResult> = _topArtistsResult
    private val _topTracksResult = MutableLiveData<TopTracksResult>()
    val topTracksResult: LiveData<TopTracksResult> = _topTracksResult

    fun getTopAlbums(activity: ProfileActivity) {
        CoroutineScope(IO).launch {
            val result = userRepository.getTopAlbums(activity)

            withContext(Main) {
                if (result is Result.Success) {
                    _topAlbumResult.value =
                        TopAlbumsResult(success = result.data.albumsContainer.albums)
                } else {
                    _topAlbumResult.value = TopAlbumsResult(error = R.string.cant_get_top_albums)
                }
            }
        }
    }

    fun getTopArtists(activity: ProfileActivity) {
        CoroutineScope(IO).launch {
            val result = userRepository.getTopArtists(activity)

            withContext(Main) {
                if (result is Result.Success) {
                    val artists = result.data.artistsContainer.artists
                    CoroutineScope(IO).launch {
                        // For each artist, get image from Spotify API
                        for (i in artists.indices) {
                            val imageResult = artistRepository.getImage(activity, artists[i].name)
                            if (imageResult is Result.Success) {
                                artists[i].spotifyImage = imageResult.data
                            } else {
                                withContext(Main) {
                                    _topArtistsResult.value =
                                        TopArtistsResult(error = R.string.cant_get_top_albums_image)
                                }
                            }
                        }
                        withContext(Main) {
                            _topArtistsResult.value =
                                TopArtistsResult(success = artists)
                        }
                    }
                } else {
                    _topArtistsResult.value = TopArtistsResult(error = R.string.cant_get_top_albums)
                }
            }
        }
    }

    fun getTopTracks(activity: ProfileActivity) {
        CoroutineScope(IO).launch {
            val result = userRepository.getTopTracks(activity)

            withContext(Main) {
                if (result is Result.Success) {
                    val tracks = result.data.tracksContainer.tracks
                    CoroutineScope(IO).launch {
                        // For each track, get image from Spotify API
                        for (i in tracks.indices) {
                            val imageResult = trackRepository.getImage(
                                activity,
                                tracks[i].name,
                                tracks[i].artist.name
                            )
                            if (imageResult is Result.Success) {
                                tracks[i].spotifyImage = imageResult.data
                            } else {
                                withContext(Main) {
                                    _topTracksResult.value =
                                        TopTracksResult(error = R.string.cant_get_top_tracks_image)
                                }
                            }
                        }
                        withContext(Main) {
                            _topTracksResult.value =
                                TopTracksResult(success = tracks)
                        }
                    }

                } else {
                    _topTracksResult.value = TopTracksResult(error = R.string.cant_get_top_tracks)
                }
            }
        }
    }
}