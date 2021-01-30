package fr.esgi.firstfm.topFive

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.esgi.firstfm.MainActivity
import fr.esgi.firstfm.R
import fr.esgi.firstfm.data.Result
import fr.esgi.firstfm.data.UserRepository
import fr.esgi.firstfm.entity.TopAlbumsResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TopFiveViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _topAlbumResult = MutableLiveData<TopAlbumsResult>()
    val topAlbumResult: LiveData<TopAlbumsResult> = _topAlbumResult

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
}