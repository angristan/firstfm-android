package fr.esgi.firstfm

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import fr.esgi.firstfm.album.AlbumDetailActivity
import fr.esgi.firstfm.artist.ArtistDetailActivity
import fr.esgi.firstfm.entity.model.Album
import fr.esgi.firstfm.entity.model.Artist
import fr.esgi.firstfm.entity.model.Image
import fr.esgi.firstfm.entity.model.Track
import fr.esgi.firstfm.profile.ProfileAdapter
import fr.esgi.firstfm.profile.ProfileViewHolder
import fr.esgi.firstfm.profile.ProfileViewModel
import fr.esgi.firstfm.ui.login.LoginActivity
import fr.esgi.firstfm.ui.login.ProfileViewModelFactory
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity(), ProfileViewHolder.OnProfileClickedListener {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var adapter: ProfileAdapter

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
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
            this@ProfileActivity.recreate()
        }

        setContentView(R.layout.activity_profile)
        setSupportActionBar(tool_bar)


        profileViewModel = ViewModelProviders.of(this, ProfileViewModelFactory())
            .get(ProfileViewModel::class.java)

        profileViewModel.getTopAlbums(this)
        profileViewModel.getTopArtists(this)
        profileViewModel.getTopTracks(this)

        profileViewModel.topAlbumResult.observe(this@ProfileActivity, Observer {
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

        profileViewModel.topArtistsResult.observe(this@ProfileActivity, Observer {
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

        profileViewModel.topTracksResult.observe(this@ProfileActivity, Observer {
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

        this.adapter = ProfileAdapter(this.albums, this.artists, this.tracks, this@ProfileActivity)
        albumRecyclerView.layoutManager = LinearLayoutManager(this@ProfileActivity)
        albumRecyclerView.adapter = this.adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id: Int = item.itemId
        return if (id == R.id.logout) {
            this.logOut()
            this@ProfileActivity.recreate()
            return true

        } else super.onOptionsItemSelected(item)
    }

    private fun logOut() {
        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences("firstfm", MODE_PRIVATE)

        val myEdit = sharedPreferences.edit()

        myEdit.remove("token")
        myEdit.remove("username")
        myEdit.apply()
    }

    private fun isLoggedIn(): Boolean {
        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences("firstfm", MODE_PRIVATE)
        return sharedPreferences.contains("token") && sharedPreferences.contains("username")
    }

    companion object {
        fun navigateTo(context: Context) {
            context.startActivity(Intent(context, ProfileActivity::class.java))
        }
    }

//    override fun onNominatedAlbumClicked(album: AlbumResponse?) {
//        // TODO update this part, navigate to album page
//        if (album != null) {
//            AlbumDetailActivity.navigateTo(this, album.mbId)
//        }
//    }

    override fun onProfileAlbumClicked(album: Album?) {
        if (album != null) {
            AlbumDetailActivity.navigateTo(this, album.artist.name, album.name)
        }
    }

    override fun onProfileArtistClicked(artist: Artist?) {
        // TODO update this part, navigate to artist page
        if (artist != null) {
            ArtistDetailActivity.navigateTo(this,artist.mbid, artist.name)
        }
    }

    override fun onProfileTrackClicked(track: Track?) {
        if (track != null) {
            TrackActivity.navigateTo(this, track.name, track.artist.name)
        }
    }
}
