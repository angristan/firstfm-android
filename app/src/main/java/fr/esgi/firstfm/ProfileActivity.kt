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
import com.squareup.picasso.Picasso
import fr.esgi.firstfm.album.AlbumDetailActivity
import fr.esgi.firstfm.artist.ArtistDetailActivity
import fr.esgi.firstfm.entity.model.*
import fr.esgi.firstfm.profile.ProfileAdapter
import fr.esgi.firstfm.profile.ProfileViewHolder
import fr.esgi.firstfm.profile.ProfileViewModel
import fr.esgi.firstfm.ui.login.LoginActivity
import fr.esgi.firstfm.ui.login.ProfileViewModelFactory
import kotlinx.android.synthetic.main.activity_album_detail.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity(), ProfileViewHolder.OnProfileClickedListener {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var adapter: ProfileAdapter
    private var albums: MutableList<Album> = mutableListOf()
    private var artists: MutableList<Artist> = mutableListOf()
    private var tracks: MutableList<Track> = mutableListOf()
    private var user = User()
    private val resourcesToLoad =
        mutableMapOf("albums" to true, "tracks" to true, "artists" to true, "user" to true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profile)
        setSupportActionBar(tool_bar)

        profileViewModel = ViewModelProviders.of(this, ProfileViewModelFactory())
            .get(ProfileViewModel::class.java)

        profileViewModel.getTopAlbums(this)
        profileViewModel.getTopArtists(this)
        profileViewModel.getTopTracks(this)
        profileViewModel.getInfo(this)

        profileViewModel.topAlbumResult.observe(this@ProfileActivity, Observer {
            val topAlbumResult = it ?: return@Observer

            if (loading != null) {
                profileLoader.visibility = View.GONE
            }
            if (topAlbumResult.error != null) {
                Toast.makeText(applicationContext, topAlbumResult.error, Toast.LENGTH_SHORT).show()

            }
            if (topAlbumResult.success != null) {
                this.albums = topAlbumResult.success as MutableList<Album>
                this.adapter.updateAlbums(albums)
                this.resourcesToLoad["albums"] = false
                this.updateLoader()
            }
        })

        profileViewModel.topArtistsResult.observe(this@ProfileActivity, Observer {
            val topArtistsResult = it ?: return@Observer

            if (loading != null) {
                profileLoader.visibility = View.GONE
            }
            if (topArtistsResult.error != null) {
                Toast.makeText(applicationContext, topArtistsResult.error, Toast.LENGTH_SHORT)
                    .show()

            }
            if (topArtistsResult.success != null) {
                this.artists = topArtistsResult.success as MutableList<Artist>
                this.adapter.updateArtists(artists)
                this.resourcesToLoad["artists"] = false
                this.updateLoader()
            }
        })

        profileViewModel.topTracksResult.observe(this@ProfileActivity, Observer {
            val topTracksResult = it ?: return@Observer

            if (loading != null) {
                profileLoader.visibility = View.GONE
            }
            if (topTracksResult.error != null) {
                Toast.makeText(applicationContext, topTracksResult.error, Toast.LENGTH_SHORT)
                    .show()

            }
            if (topTracksResult.success != null) {
                this.tracks = topTracksResult.success as MutableList<Track>
                this.adapter.updateTracks(tracks)
                this.resourcesToLoad["tracks"] = false
                this.updateLoader()
            }
        })

        profileViewModel.userInfoResult.observe(this@ProfileActivity, Observer {
            val userInfoResult = it ?: return@Observer

            if (loading != null) {
                profileLoader.visibility = View.GONE
            }
            if (userInfoResult.error != null) {
                Toast.makeText(applicationContext, userInfoResult.error, Toast.LENGTH_SHORT).show()

            }
            if (userInfoResult.success != null) {
                this.user = userInfoResult.success

                Picasso.get()
                    .load(this.user.images[this.user.images.size - 1].url)
                    .into(profileImage)

                profileUsername.text = resources.getString(R.string.profileUsername, user.name)
                val scrobbles = formatNumberToString(user.playcount)
                profileScroblesCount.text =
                    resources.getString(R.string.profile_scrobbles, scrobbles)

                this.resourcesToLoad["user"] = false
                this.updateLoader()
            }
        })

        this.adapter = ProfileAdapter(this.albums, this.artists, this.tracks, this@ProfileActivity)
        profileRecyclerView.layoutManager = LinearLayoutManager(this@ProfileActivity)
        profileRecyclerView.adapter = this.adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun updateLoader() {
        val notLoaded = this.resourcesToLoad.filter { (_, value) -> value }
        if (notLoaded.isEmpty()) {
            profileLoader.visibility = View.GONE
            profileScrollView.visibility = View.VISIBLE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id: Int = item.itemId
        return if (id == R.id.logout) {
            this.logOut()
            LoginActivity.navigateTo(this)
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

    override fun onProfileAlbumClicked(album: Album?) {
        if (album != null) {
            AlbumDetailActivity.navigateTo(this, album.artist.name, album.name)
        }
    }

    override fun onProfileArtistClicked(artist: Artist?) {
        // TODO update this part, navigate to artist page
        if (artist != null) {
            ArtistDetailActivity.navigateTo(this, artist.name)
        }
    }

    override fun onProfileTrackClicked(track: Track?) {
        if (track != null) {
            TrackActivity.navigateTo(this, track.name, track.artist.name)
        }
    }
}
