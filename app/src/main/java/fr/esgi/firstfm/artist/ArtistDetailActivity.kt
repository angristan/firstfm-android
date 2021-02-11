package fr.esgi.firstfm.artist

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import fr.esgi.firstfm.R
import fr.esgi.firstfm.album.AlbumDetailActivity
import fr.esgi.firstfm.album.albumList.AlbumAdapter
import fr.esgi.firstfm.album.albumList.AlbumViewHolder
import fr.esgi.firstfm.entity.SpotifyArtistSearchResponse
import fr.esgi.firstfm.lastfmapi.*
import fr.esgi.firstfm.lastfmapi.LastFmApi.retrieveArtistInfo
import fr.esgi.firstfm.lastfmapi.LastFmApi.retrieveArtistTopAlbumsInfo
import fr.esgi.firstfm.spotifyapi.SpotifyApi.searchArtistInfo
import kotlinx.android.synthetic.main.activity_artist_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArtistDetailActivity : AppCompatActivity(), AlbumViewHolder.OnAlbumClickedListener {

    private var artist: ArtistResponse? = null
    private var albums: List<AlbumFromTop>? = null

    companion object {
        private val PARAM2: String = "artistName"

        fun navigateTo(context: Context, param2: String?) {
            val intent = Intent(context, ArtistDetailActivity::class.java).apply {
                putExtra(PARAM2, param2)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artist_detail)

        val receivedArtistName = intent?.getStringExtra("artistName")

        loader?.visibility = View.VISIBLE
        scrollView?.visibility = View.GONE
        noTrackList?.visibility = View.GONE

        if (isNetworkConnected()) {
            CoroutineScope(Dispatchers.IO).launch {

                searchArtistInfo(this@ArtistDetailActivity, receivedArtistName,
                    object : Callback<SpotifyArtistSearchResponse> {
                        override fun onResponse(
                            call: Call<SpotifyArtistSearchResponse>,
                            response: Response<SpotifyArtistSearchResponse>
                        ) {

                            val image =
                                response.body()?.artistsContainer?.artistsResults?.get(0)?.images?.get(
                                    0
                                )
                            Picasso.get()
                                .load(image?.url)
                                .into(artistDetailImage,
                                    object : com.squareup.picasso.Callback {
                                        override fun onSuccess() {}

                                        override fun onError(e: Exception) {
                                            artistDetailImage?.setImageResource(R.drawable.default_album_picture)
                                        }
                                    }
                                )

                        }

                        override fun onFailure(
                            call: Call<SpotifyArtistSearchResponse>,
                            t: Throwable
                        ) {
                            Log.d("testSpot", "onFailure")
                        }
                    })
            }

            retrieveArtistInfo(
                receivedArtistName,
                object : Callback<LastFmApiArtistGetInfoResponse> {

                    override fun onResponse(
                        call: Call<LastFmApiArtistGetInfoResponse>,
                        response: Response<LastFmApiArtistGetInfoResponse>
                    ) {
                        loader?.visibility = View.GONE
                        scrollView?.visibility = View.VISIBLE
                        artist = response.body()?.artist

                        artistDetailName?.text = artist?.name

                        listenersNumber.text = formatNumberToString(artist?.stats?.listeners)
                        scrobblesNumber.text = formatNumberToString(artist?.stats?.playCount)

                        onTour?.setTextColor(
                            ContextCompat.getColor(
                                this@ArtistDetailActivity,
                                R.color.colorRed
                            )
                        )

                        if (artist?.onTour.equals("0")) {
                            onTour?.visibility = View.GONE
                        }
                    }

                    override fun onFailure(
                        call: Call<LastFmApiArtistGetInfoResponse>,
                        t: Throwable
                    ) {
                        loader?.visibility = View.GONE
                        val toast = Toast.makeText(
                            applicationContext,
                            "Error while loading detail page content",
                            Toast.LENGTH_SHORT
                        )
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
                        toast.show()
                    }
                })


            retrieveArtistTopAlbumsInfo(
                receivedArtistName,
                object : Callback<LastFmApiArtistTopAlbumsGetInfoResponse> {

                    override fun onResponse(
                        call: Call<LastFmApiArtistTopAlbumsGetInfoResponse>,
                        response: Response<LastFmApiArtistTopAlbumsGetInfoResponse>
                    ) {
                        deleteNameLessAlbumFromList(response.body()?.albums?.albums)
                        loader?.visibility = View.GONE

                        if (albums?.size == 0) {
                            recyclerView?.visibility = View.GONE
                            noTrackList?.visibility = View.VISIBLE
                        }

                        recyclerView?.apply {
                            layoutManager = LinearLayoutManager(this@ArtistDetailActivity)
                            adapter = albums?.let { AlbumAdapter(it, this@ArtistDetailActivity) }
                        }
                    }

                    override fun onFailure(
                        call: Call<LastFmApiArtistTopAlbumsGetInfoResponse>,
                        t: Throwable
                    ) {
                        loader?.visibility = View.GONE
                        val toast = Toast.makeText(
                            applicationContext,
                            "Error while loading list content",
                            Toast.LENGTH_SHORT
                        )
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
                        toast.show()
                    }

                })

        } else {
            loader?.visibility = View.GONE
            val toast =
                Toast.makeText(applicationContext, "No internet connection", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
            toast.show()
        }
    }


    override fun onAlbumClicked(album: AlbumFromTop?) {
        if (album != null) {
            AlbumDetailActivity.navigateTo(this, album.artist.name, album.name)
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

    private fun deleteNameLessAlbumFromList(albums: List<AlbumFromTop>?) {
        if (albums != null) {
            this.albums = albums.filter { x: AlbumFromTop? -> x?.name != "(null)" }
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