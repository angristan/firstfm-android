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
import fr.esgi.firstfm.R
import fr.esgi.firstfm.TrackActivity
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
        private val PARAM2: String = "artistName"
        private val PARAM3: String = "albumName"

        fun navigateTo(context: Context, param2: String?, param3: String?) {
            val intent = Intent(context, AlbumDetailActivity::class.java).apply {
                putExtra(PARAM2, param2)
                putExtra(PARAM3, param3)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_detail)


        val receivedArtistName = intent?.getStringExtra("artistName")
        val receivedAlbumName = intent?.getStringExtra("albumName")

        loader?.visibility = View.VISIBLE
        scrollView?.visibility = View.GONE

        if (isNetworkConnected()) {
            retrieveAlbumInfo(receivedArtistName, receivedAlbumName, this)
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

        val body = response.body()

        album = body?.album
        tracks = body?.album?.tracks?.tracks

        albumDetailName?.text = album?.name
        albumDetailArtistName?.text = album?.artist

        listeners?.text = resources.getString(R.string.listeners_with_value, album?.listeners)
        playCount?.text = resources.getString(R.string.play_count_with_value, album?.playCount)
        trackListTitleTextView?.text = resources.getString(R.string.track_list_title, album?.name)

        for (i in 3 downTo 0) {
            if (album?.images?.get(i)?.url != "") {
                Picasso.get()
                    .load(album?.images?.get(i)?.url)
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
            TrackActivity.navigateTo(this, track.name, this.album?.artist)
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
}