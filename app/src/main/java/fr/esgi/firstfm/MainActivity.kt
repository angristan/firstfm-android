package fr.esgi.firstfm

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import fr.esgi.firstfm.entity.model.Album
import fr.esgi.firstfm.entity.model.Artist
import fr.esgi.firstfm.entity.model.Image
import fr.esgi.firstfm.entity.model.Track
import fr.esgi.firstfm.topFive.NominatedViewHolder
import fr.esgi.firstfm.topFive.TopFiveAdapter
import fr.esgi.firstfm.topFive.TopFiveViewModel
import fr.esgi.firstfm.ui.login.LoginActivity
import fr.esgi.firstfm.ui.login.TopFiveViewModelFactory
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NominatedViewHolder.OnNominatedClickedListener {

    private lateinit var topFiveViewModel: TopFiveViewModel
    private lateinit var adapter: TopFiveAdapter


    private var albums: MutableList<Album> = mutableListOf(
        Album(
            name = "Album_1",
            image = listOf(Image(url = "https://lastfm.freetls.fastly.net/i/u/770x0/11dd7e48a1f042c688bf54985f01d088.webp#11dd7e48a1f042c688bf54985f01d088"))
        ),
        Album(
            name = "Album_2",
            image = listOf(Image(url = "https://lastfm.freetls.fastly.net/i/u/770x0/11dd7e48a1f042c688bf54985f01d088.webp#11dd7e48a1f042c688bf54985f01d088"))
        ),
        Album(
            name = "Album_3",
            image = listOf(Image(url = "https://lastfm.freetls.fastly.net/i/u/770x0/11dd7e48a1f042c688bf54985f01d088.webp#11dd7e48a1f042c688bf54985f01d088"))
        ),
        Album(
            name = "Album_4",
            image = listOf(Image(url = "https://lastfm.freetls.fastly.net/i/u/770x0/11dd7e48a1f042c688bf54985f01d088.webp#11dd7e48a1f042c688bf54985f01d088"))
        ),
        Album(
            name = "Album_5",
            image = listOf(Image(url = "https://lastfm.freetls.fastly.net/i/u/770x0/11dd7e48a1f042c688bf54985f01d088.webp#11dd7e48a1f042c688bf54985f01d088"))
        )
    )

    private var artists = mutableListOf(
        Artist(
            name = "Artist_1",
            images = listOf(Image(url = "https://lastfm.freetls.fastly.net/i/u/770x0/fcb1563652a613b27c2fcf4d1bd0cf6a.webp#fcb1563652a613b27c2fcf4d1bd0cf6a"))
        ),
        Artist(
            name = "Artist_2",
            images = listOf(Image(url = "https://lastfm.freetls.fastly.net/i/u/770x0/fcb1563652a613b27c2fcf4d1bd0cf6a.webp#fcb1563652a613b27c2fcf4d1bd0cf6a"))
        ),
        Artist(
            name = "Artist_3",
            images = listOf(Image(url = "https://lastfm.freetls.fastly.net/i/u/770x0/fcb1563652a613b27c2fcf4d1bd0cf6a.webp#fcb1563652a613b27c2fcf4d1bd0cf6a"))
        ),
        Artist(
            name = "Artist_4",
            images = listOf(Image(url = "https://lastfm.freetls.fastly.net/i/u/770x0/fcb1563652a613b27c2fcf4d1bd0cf6a.webp#fcb1563652a613b27c2fcf4d1bd0cf6a"))
        ),
        Artist(
            name = "Artist_5",
            images = listOf(Image(url = "https://lastfm.freetls.fastly.net/i/u/770x0/fcb1563652a613b27c2fcf4d1bd0cf6a.webp#fcb1563652a613b27c2fcf4d1bd0cf6a"))
        )
    )

    private var tracks = mutableListOf(
        Track(
            "Track_1",
            "TEST",
            image = listOf(Image(url = "https://lastfm.freetls.fastly.net/i/u/770x0/686cff186b134340827fa08930c04ff1.webp#686cff186b134340827fa08930c04ff1"))
        ),
        Track(
            "Track_2",
            "TEST",
            image = listOf(Image(url = "https://lastfm.freetls.fastly.net/i/u/770x0/686cff186b134340827fa08930c04ff1.webp#686cff186b134340827fa08930c04ff1"))
        ),
        Track(
            "Track_3",
            "TEST",
            image = listOf(Image(url = "https://lastfm.freetls.fastly.net/i/u/770x0/686cff186b134340827fa08930c04ff1.webp#686cff186b134340827fa08930c04ff1"))
        ),
        Track(
            "Track_4",
            "TEST",
            image = listOf(Image(url = "https://lastfm.freetls.fastly.net/i/u/770x0/686cff186b134340827fa08930c04ff1.webp#686cff186b134340827fa08930c04ff1"))
        ),
        Track(
            "Track_5",
            "TEST",
            image = listOf(Image(url = "https://lastfm.freetls.fastly.net/i/u/770x0/686cff186b134340827fa08930c04ff1.webp#686cff186b134340827fa08930c04ff1"))
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isLoggedIn()) {
            startActivity(Intent(this, LoginActivity::class.java))
            onCreate(savedInstanceState)
        }

        setContentView(R.layout.activity_main)

        topFiveViewModel = ViewModelProviders.of(this, TopFiveViewModelFactory())
            .get(TopFiveViewModel::class.java)

        topFiveViewModel.getTopAlbums(this)
        topFiveViewModel.getTopArtists(this)
        topFiveViewModel.getTopTracks(this)

        topFiveViewModel.topAlbumResult.observe(this@MainActivity, Observer {
            val topAlbumResult = it ?: return@Observer

            if (loading != null) {
                loading.visibility = View.GONE
            }
            if (topAlbumResult.error != null) {
                Toast.makeText(applicationContext, topAlbumResult.error, Toast.LENGTH_SHORT).show()

            }
            if (topAlbumResult.success != null) {
                this.albums = topAlbumResult.success as MutableList<Album>
                this.adapter.updateAlbums(albums)
            }
        })

        topFiveViewModel.topArtistsResult.observe(this@MainActivity, Observer {
            val topArtistsResult = it ?: return@Observer

            if (loading != null) {
                loading.visibility = View.GONE
            }
            if (topArtistsResult.error != null) {
                Toast.makeText(applicationContext, topArtistsResult.error, Toast.LENGTH_SHORT)
                    .show()

            }
            if (topArtistsResult.success != null) {
                this.artists = topArtistsResult.success as MutableList<Artist>
                this.adapter.updateArtists(artists)
            }
        })

        topFiveViewModel.topTracksResult.observe(this@MainActivity, Observer {
            val topTracksResult = it ?: return@Observer

            if (loading != null) {
                loading.visibility = View.GONE
            }
            if (topTracksResult.error != null) {
                Toast.makeText(applicationContext, topTracksResult.error, Toast.LENGTH_SHORT)
                    .show()

            }
            if (topTracksResult.success != null) {
                this.tracks = topTracksResult.success as MutableList<Track>
                this.adapter.updateTracks(tracks)
            }
        })

        this.adapter = TopFiveAdapter(this.albums, this.artists, this.tracks, this@MainActivity)
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        recyclerView.adapter = this.adapter
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

//    override fun onNominatedAlbumClicked(album: AlbumResponse?) {
//        // TODO update this part, navigate to album page
//        if (album != null) {
//            AlbumDetailActivity.navigateTo(this, album.mbId)
//        }
//    }

    override fun onNominatedAlbumClicked(album: Album?) {
        TODO("Not yet implemented")
    }

    override fun onNominatedArtistClicked(artist: Artist?) {
        // TODO update this part, navigate to artist page
        if (artist != null) {
//            AlbumDetailActivity.navigateTo(this, artist.album, artist.artist)
        }
    }

    override fun onNominatedTrackClicked(track: Track?) {
        // TODO update this part, navigate to track page
        if (track != null) {
//            AlbumDetailActivity.navigateTo(this, track.album, track.artist)
        }
    }
}
