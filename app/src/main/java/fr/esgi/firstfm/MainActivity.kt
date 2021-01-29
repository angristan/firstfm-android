package fr.esgi.firstfm

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import fr.esgi.firstfm.album.AlbumDetailActivity
import fr.esgi.firstfm.objects.Album
import fr.esgi.firstfm.objects.Artist
import fr.esgi.firstfm.objects.Track
import fr.esgi.firstfm.topFive.NominatedViewHolder
import fr.esgi.firstfm.topFive.TopFiveAdapter
import fr.esgi.firstfm.topFive.TopFiveViewModel
import fr.esgi.firstfm.ui.login.LoginActivity
import fr.esgi.firstfm.ui.login.TopFiveViewModelFactory
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NominatedViewHolder.OnNominatedClickedListener {

    private lateinit var topFiveViewModel: TopFiveViewModel


    private var albums: List<Album> = listOf()

    private val artists = listOf(
        Artist(
            name = "Artist_1",
            image = "https://lastfm.freetls.fastly.net/i/u/770x0/fcb1563652a613b27c2fcf4d1bd0cf6a.webp#fcb1563652a613b27c2fcf4d1bd0cf6a"
        ),
        Artist(
            name = "Artist_2",
            image = "https://lastfm.freetls.fastly.net/i/u/770x0/fcb1563652a613b27c2fcf4d1bd0cf6a.webp#fcb1563652a613b27c2fcf4d1bd0cf6a"
        ),
        Artist(
            name = "Artist_3",
            image = "https://lastfm.freetls.fastly.net/i/u/770x0/fcb1563652a613b27c2fcf4d1bd0cf6a.webp#fcb1563652a613b27c2fcf4d1bd0cf6a"
        ),
        Artist(
            name = "Artist_4",
            image = "https://lastfm.freetls.fastly.net/i/u/770x0/fcb1563652a613b27c2fcf4d1bd0cf6a.webp#fcb1563652a613b27c2fcf4d1bd0cf6a"
        ),
        Artist(
            name = "Artist_5",
            image = "https://lastfm.freetls.fastly.net/i/u/770x0/fcb1563652a613b27c2fcf4d1bd0cf6a.webp#fcb1563652a613b27c2fcf4d1bd0cf6a"
        )
    )

    private val tracks = listOf(
        Track(
            "Track_1",
            "TEST",
            "https://lastfm.freetls.fastly.net/i/u/770x0/686cff186b134340827fa08930c04ff1.webp#686cff186b134340827fa08930c04ff1"
        ),
        Track(
            "Track_2",
            "TEST",
            "https://lastfm.freetls.fastly.net/i/u/770x0/686cff186b134340827fa08930c04ff1.webp#686cff186b134340827fa08930c04ff1"
        ),
        Track(
            "Track_3",
            "TEST",
            "https://lastfm.freetls.fastly.net/i/u/770x0/686cff186b134340827fa08930c04ff1.webp#686cff186b134340827fa08930c04ff1"
        ),
        Track(
            "Track_4",
            "TEST",
            "https://lastfm.freetls.fastly.net/i/u/770x0/686cff186b134340827fa08930c04ff1.webp#686cff186b134340827fa08930c04ff1"
        ),
        Track(
            "Track_5",
            "TEST",
            "https://lastfm.freetls.fastly.net/i/u/770x0/686cff186b134340827fa08930c04ff1.webp#686cff186b134340827fa08930c04ff1"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {

        if (!isLoggedIn()) {
            LoginActivity.navigateTo(this)
        }

        Log.v("wesh", "coucou")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        topFiveViewModel = ViewModelProviders.of(this, TopFiveViewModelFactory())
            .get(TopFiveViewModel::class.java)

        topFiveViewModel.getTopAlbums(this)

        topFiveViewModel.topAlbumResult.observe(this@MainActivity, Observer {
            Log.v("wesh", "observe")
            val topAlbumResult = it ?: return@Observer

            Log.v("wesh", topAlbumResult.toString())


            if (loading != null) {
                loading.visibility = View.GONE
            }
            if (topAlbumResult.error != null) {
                Toast.makeText(applicationContext, topAlbumResult.error, Toast.LENGTH_SHORT).show()

            }
            if (topAlbumResult.success != null) {

                this.albums = topAlbumResult.success
                recyclerView?.apply {
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    adapter = TopFiveAdapter(albums, artists, tracks, this@MainActivity)
                    (adapter as TopFiveAdapter).notifyDataSetChanged()
                }
            }
        })


        recyclerView?.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = TopFiveAdapter(albums, artists, tracks, this@MainActivity)
        }
    }

    private fun isLoggedIn(): Boolean {
        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences("firstfm", MODE_PRIVATE)
        return sharedPreferences.contains("token") && sharedPreferences.contains("username")
    }

    companion object {
        fun navigateTo(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    override fun onNominatedAlbumClicked(album: Album?) {
        // TODO update this part, navigate to album page
        if (album != null) {
            AlbumDetailActivity.navigateTo(this, album.name, album.artist.name)
        }
    }

    override fun onNominatedArtistClicked(artist: Artist?) {
        // TODO update this part, navigate to artist page
        if (artist != null) {
            AlbumDetailActivity.navigateTo(this, artist.name, artist.url)
        }
    }

    override fun onNominatedTrackClicked(track: Track?) {
        // TODO update this part, navigate to track page
        if (track != null) {
            AlbumDetailActivity.navigateTo(this, track.album, track.artist)
        }
    }
}
