package fr.esgi.firstfm.album

import LastFmApiAlbumGetInfoResponse
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import fr.esgi.firstfm.MusicActivity
import fr.esgi.firstfm.R
import fr.esgi.firstfm.lastfmapi.AlbumResponse
import fr.esgi.firstfm.lastfmapi.LastFmApi.retrieveAlbumInfo
import fr.esgi.firstfm.lastfmapi.TrackResponse
import fr.esgi.firstfm.track.tracklist.TrackAdapter
import fr.esgi.firstfm.track.tracklist.TrackViewHolder
import kotlinx.android.synthetic.main.activity_album_detail.*
import kotlinx.android.synthetic.main.activity_album_detail.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumDetailActivity : AppCompatActivity(), TrackViewHolder.OnTrackClickedListener,
    Callback<LastFmApiAlbumGetInfoResponse> {

    private var album: AlbumResponse? = null
    private var tracks: List<TrackResponse>? = null

    companion object {
        private val PARAM1: String = "mbId"
        private val PARAM2: String = "artistName"
        private val PARAM3: String = "albumName"

        fun navigateTo(context: Context, param1: String?, param2: String?, param3: String?) {
            val intent = Intent(context, AlbumDetailActivity::class.java).apply {
                putExtra(PARAM1, param1)
                putExtra(PARAM2, param2)
                putExtra(PARAM3, param3)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_detail)

        val receivedMbId = intent?.getStringExtra("mbId")
        val receivedArtistName = intent?.getStringExtra("artistName")
        val receivedAlbumName = intent?.getStringExtra("albumName")

        loader?.visibility = View.VISIBLE
        scrollView?.visibility = View.GONE
        noTrackList?.visibility = View.GONE

        if (isNetworkConnected()) {
            retrieveAlbumInfo(receivedMbId, receivedArtistName, receivedAlbumName, this)
        } else {
            loader?.visibility = View.GONE
            val toast =
                Toast.makeText(applicationContext, "No internet connection", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
            toast.show()
        }
    }

    override fun onResponse(
        call: Call<LastFmApiAlbumGetInfoResponse>,
        response: Response<LastFmApiAlbumGetInfoResponse>
    ) {
        loader?.visibility = View.GONE
        scrollView?.visibility = View.VISIBLE

        album = response.body()?.album
        tracks = response.body()?.album?.tracks?.tracks

        albumDetailName?.text = album?.name
        albumDetailArtistName?.text = album?.artist

        if (album?.tracks?.tracks?.size == 0) {
            recyclerView?.visibility = View.GONE
            noTrackList?.visibility = View.VISIBLE
        }

        listenersNumber?.text = formatNumberToString(album?.listeners)
        scrobblesNumber?.text = formatNumberToString(album?.playCount)

        this.album?.let { selectBiggestPicture(it) }

        recyclerView?.apply {
            layoutManager = LinearLayoutManager(this@AlbumDetailActivity)
            adapter = tracks?.let { TrackAdapter(it, this@AlbumDetailActivity) }
        }
    }

    override fun onFailure(call: Call<LastFmApiAlbumGetInfoResponse>, t: Throwable) {
        loader?.visibility = View.GONE
        val toast = Toast.makeText(
            applicationContext,
            "Error while loading detail page content",
            Toast.LENGTH_SHORT
        )
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
        toast.show()
    }

    override fun onTrackClicked(track: TrackResponse?) {
        if (track != null) {
            MusicActivity.navigateTo(this, track.name, this.album?.artist)
        }
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
            networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            true
            // TODO("VERSION.SDK_INT < M")
        }
    }

    fun selectBiggestPicture(album: AlbumResponse) {
        for (i in 3 downTo 0) {
            if (album.images.get(i).url != "") {
                Picasso.get()
                    .load(album.images.get(i).url)
                    .into(albumDetailImage,
                        object : com.squareup.picasso.Callback {
                            override fun onSuccess() {}

                            override fun onError(e: Exception) {
                                albumDetailImage?.setImageResource(R.drawable.default_album_picture)
                            }
                        }
                    )
                break
            }
        }
    }

    private fun formatNumberToString(listeners: Long?): String {
        if (listeners != null) {
            return when {
                listeners < 1000 -> {
                    "$listeners"
                }
                listeners in 1000..999999 -> {
                    val unit = listeners / 1000
                    val rest = listeners % 1000 / 100
                    "${unit}.${rest}K"
                }
                else -> {
                    val unit = listeners / 1000000
                    val rest = listeners % 1000000 / 1000
                    "${unit}.${rest}M"
                }
            }
        }

        return ""
    }
}