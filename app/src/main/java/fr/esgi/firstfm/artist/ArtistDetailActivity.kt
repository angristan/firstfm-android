package fr.esgi.firstfm.artist

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
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import fr.esgi.firstfm.R
import fr.esgi.firstfm.album.AlbumDetailActivity
import fr.esgi.firstfm.album.albumList.AlbumRecyclerActivity
import fr.esgi.firstfm.album.albumList.AlbumViewHolder
import fr.esgi.firstfm.lastfmapi.*
import fr.esgi.firstfm.lastfmapi.LastFmApi.retrieveArtistInfoByMbId
import kotlinx.android.synthetic.main.activity_artist_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArtistDetailActivity: AppCompatActivity(), AlbumViewHolder.OnAlbumClickedListener,
        Callback<LastFmApiArtistGetInfoResponse>, View.OnClickListener {

    private var artist: ArtistResponse? = null
    private var albums: List<TopAlbums>? = null

    companion object {
        private val PARAM1: String = "mbId"
        private val PARAM2: String = "artistName"

        fun navigateTo(context: Context, param1: String?, param2: String?) {
            val intent = Intent(context, ArtistDetailActivity::class.java).apply {
                putExtra(PARAM1, param1)
                putExtra(PARAM2, param2)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artist_detail)

        val receivedMbId = intent?.getStringExtra("mbId")
        val receivedArtistName = intent?.getStringExtra("artistName")

        loader?.visibility = View.VISIBLE
        scrollView?.visibility = View.GONE

        if (isNetworkConnected()) {
            val id= "6cad3ce5-6380-4594-a8da-ae7d273b683d"
            val name = "Claire Laffut"
            retrieveArtistInfoByMbId("", name, this)
//            retrieveArtistInfoByMbId(id, "", this)
//            retrieveArtistInfoByMbId(receivedMbId, receivedArtistName, this)
        } else {
            loader?.visibility = View.GONE
            val toast =
                    Toast.makeText(applicationContext, "No internet connection", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
            toast.show()
        }
    }

    override fun onResponse(call: Call<LastFmApiArtistGetInfoResponse>, response: Response<LastFmApiArtistGetInfoResponse>) {
        loader?.visibility = View.GONE
        scrollView?.visibility = View.VISIBLE

        artist = response.body()?.artist

        artistDetailName?.text = artist?.name

        listeners?.text = resources.getString(R.string.listeners_with_value, artist?.stats?.listeners)
        listened?.text = resources.getString(R.string.listened_with_value, artist?.stats?.playCount)
        onTour?.text = resources.getString(R.string.on_tour_value, "ON TOUR")
        onTour?.setTextColor(ContextCompat.getColor(this, R.color.colorRed))

        if(artist?.onTour.equals("0")){
            onTour?.visibility = View.GONE
        }

        button?.setOnClickListener(this)

        for (i in 3 downTo 0) {
            if (artist?.images?.get(i)?.url != "") {
                Picasso.get()
                        .load(artist?.images?.get(i)?.url)
                        .into(artistDetailImage,
                                object : com.squareup.picasso.Callback {
                                    override fun onSuccess() {}

                                    override fun onError(e: Exception) {
                                        artistDetailImage?.setImageResource(R.drawable.default_artist_picture)
                                    }
                                }
                        )
                break
            }
        }
//        recyclerView?.apply {
//            layoutManager = LinearLayoutManager(this@AlbumDetailActivity)
//            adapter = tracks?.let { TrackAdapter(it, this@AlbumDetailActivity) }
//        }
    }

    override fun onFailure(call: Call<LastFmApiArtistGetInfoResponse>, t: Throwable) {
        loader?.visibility = View.GONE
        val toast = Toast.makeText(
                applicationContext,
                "Error while loading detail page content",
                Toast.LENGTH_SHORT
        )
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
        toast.show()
    }

    override fun onAlbumClicked(album: AlbumFromTop?) {
        if (album != null){
            AlbumDetailActivity.navigateTo(this, album.mbId, album.artist.name, album.name)
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

    override fun onClick(v: View?) {
        AlbumRecyclerActivity.navigateTo(this);
    }
}