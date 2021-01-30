package fr.esgi.firstfm.topFive

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.esgi.firstfm.MainActivity
import fr.esgi.firstfm.R
import fr.esgi.firstfm.data.Result
import fr.esgi.firstfm.data.User.UserRepository
import fr.esgi.firstfm.entity.TopAlbumsResult
import fr.esgi.firstfm.entity.TopArtistsResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TopFiveViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _topAlbumResult = MutableLiveData<TopAlbumsResult>()
    val topAlbumResult: LiveData<TopAlbumsResult> = _topAlbumResult
    private val _topArtistsResult = MutableLiveData<TopArtistsResult>()
    val topArtistsResult: LiveData<TopArtistsResult> = _topArtistsResult

    fun getTopAlbums(activity: MainActivity) {
        // can be launched in a separate asynchronous job
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

    fun getTopArtists(activity: MainActivity) {
        // can be launched in a separate asynchronous job
        CoroutineScope(IO).launch {
            val result = userRepository.getTopArtists(activity)

            withContext(Main) {
                if (result is Result.Success) {
                    _topArtistsResult.value =
                        TopArtistsResult(success = result.data.artistsContainer.artists)
                } else {
                    _topArtistsResult.value = TopArtistsResult(error = R.string.cant_get_top_albums)
                }
            }
        }
    }
}