package fr.esgi.firstfm

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import fr.esgi.firstfm.album.AlbumDetailActivity
import fr.esgi.firstfm.lastfmapi.AlbumResponse
import fr.esgi.firstfm.objects.Album
import fr.esgi.firstfm.objects.Artist
import fr.esgi.firstfm.objects.Track
import fr.esgi.firstfm.topFive.TopFiveAdapter
import fr.esgi.firstfm.topFive.NominatedViewHolder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NominatedViewHolder.OnNominatedClickedListener {

    private val albums = listOf(
            Album("Album_1", "TEST", "https://lastfm.freetls.fastly.net/i/u/770x0/11dd7e48a1f042c688bf54985f01d088.webp#11dd7e48a1f042c688bf54985f01d088"),
            Album("Album_2", "TEST", "https://lastfm.freetls.fastly.net/i/u/770x0/11dd7e48a1f042c688bf54985f01d088.webp#11dd7e48a1f042c688bf54985f01d088"),
            Album("Album_3", "TEST", "https://lastfm.freetls.fastly.net/i/u/770x0/11dd7e48a1f042c688bf54985f01d088.webp#11dd7e48a1f042c688bf54985f01d088"),
            Album("Album_4", "TEST", "https://lastfm.freetls.fastly.net/i/u/770x0/11dd7e48a1f042c688bf54985f01d088.webp#11dd7e48a1f042c688bf54985f01d088"),
            Album("Album_5", "TEST", "https://lastfm.freetls.fastly.net/i/u/770x0/11dd7e48a1f042c688bf54985f01d088.webp#11dd7e48a1f042c688bf54985f01d088")
    )

    private val artists = listOf(
            Artist("Artist_1", "TEST", "https://lastfm.freetls.fastly.net/i/u/770x0/fcb1563652a613b27c2fcf4d1bd0cf6a.webp#fcb1563652a613b27c2fcf4d1bd0cf6a"),
            Artist("Artist_2", "TEST", "https://lastfm.freetls.fastly.net/i/u/770x0/fcb1563652a613b27c2fcf4d1bd0cf6a.webp#fcb1563652a613b27c2fcf4d1bd0cf6a"),
            Artist("Artist_3", "TEST", "https://lastfm.freetls.fastly.net/i/u/770x0/fcb1563652a613b27c2fcf4d1bd0cf6a.webp#fcb1563652a613b27c2fcf4d1bd0cf6a"),
            Artist("Artist_4", "TEST", "https://lastfm.freetls.fastly.net/i/u/770x0/fcb1563652a613b27c2fcf4d1bd0cf6a.webp#fcb1563652a613b27c2fcf4d1bd0cf6a"),
            Artist("Artist_5", "TEST", "https://lastfm.freetls.fastly.net/i/u/770x0/fcb1563652a613b27c2fcf4d1bd0cf6a.webp#fcb1563652a613b27c2fcf4d1bd0cf6a")
    )

    private val tracks = listOf(
            Track("Track_1", "TEST", "https://lastfm.freetls.fastly.net/i/u/770x0/686cff186b134340827fa08930c04ff1.webp#686cff186b134340827fa08930c04ff1"),
            Track("Track_2", "TEST", "https://lastfm.freetls.fastly.net/i/u/770x0/686cff186b134340827fa08930c04ff1.webp#686cff186b134340827fa08930c04ff1"),
            Track("Track_3", "TEST", "https://lastfm.freetls.fastly.net/i/u/770x0/686cff186b134340827fa08930c04ff1.webp#686cff186b134340827fa08930c04ff1"),
            Track("Track_4", "TEST", "https://lastfm.freetls.fastly.net/i/u/770x0/686cff186b134340827fa08930c04ff1.webp#686cff186b134340827fa08930c04ff1"),
            Track("Track_5", "TEST", "https://lastfm.freetls.fastly.net/i/u/770x0/686cff186b134340827fa08930c04ff1.webp#686cff186b134340827fa08930c04ff1")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView?.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = TopFiveAdapter(albums, artists, tracks, this@MainActivity)
        }
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