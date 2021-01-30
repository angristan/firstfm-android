package fr.esgi.firstfm.album.albumList

import Image
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import fr.esgi.firstfm.R
import fr.esgi.firstfm.album.AlbumDetailActivity
import fr.esgi.firstfm.entity.Album
import kotlinx.android.synthetic.main.activity_album_recycler.*
import kotlinx.android.synthetic.main.fragment_main_menu.*

class AlbumRecyclerActivity : AppCompatActivity(), AlbumViewHolder.OnAlbumClickedListener {
    private val albums = listOf(
        Album(
            name = "Lorem ipsum",
            image = listOf(Image(url = "https://lastfm.freetls.fastly.net/i/u/770x0/11dd7e48a1f042c688bf54985f01d088.webp#11dd7e48a1f042c688bf54985f01d088"))
        ),
        Album(
            name = "Lorem ipsum",
            image = listOf(Image(url = "https://lastfm.freetls.fastly.net/i/u/770x0/11dd7e48a1f042c688bf54985f01d088.webp#11dd7e48a1f042c688bf54985f01d088"))
        ),
        Album(
            name = "Lorem ipsum",
            image = listOf(Image(url = "https://lastfm.freetls.fastly.net/i/u/770x0/11dd7e48a1f042c688bf54985f01d088.webp#11dd7e48a1f042c688bf54985f01d088"))
        ),
        Album(
            name = "Lorem ipsum",
            image = listOf(Image(url = "https://lastfm.freetls.fastly.net/i/u/770x0/11dd7e48a1f042c688bf54985f01d088.webp#11dd7e48a1f042c688bf54985f01d088"))
        ),
        Album(
            name = "Lorem ipsum",
            image = listOf(Image(url = "https://lastfm.freetls.fastly.net/i/u/770x0/11dd7e48a1f042c688bf54985f01d088.webp#11dd7e48a1f042c688bf54985f01d088"))
        ),
        Album(
            name = "Lorem ipsum",
            image = listOf(Image(url = "https://lastfm.freetls.fastly.net/i/u/770x0/11dd7e48a1f042c688bf54985f01d088.webp#11dd7e48a1f042c688bf54985f01d088"))
        ),
        Album(
            name = "Lorem ipsum",
            image = listOf(Image(url = "https://lastfm.freetls.fastly.net/i/u/770x0/11dd7e48a1f042c688bf54985f01d088.webp#11dd7e48a1f042c688bf54985f01d088"))
        ),
        Album(
            name = "Lorem ipsum",
            image = listOf(Image(url = "https://lastfm.freetls.fastly.net/i/u/770x0/11dd7e48a1f042c688bf54985f01d088.webp#11dd7e48a1f042c688bf54985f01d088"))
        )
    )

    companion object {
        fun navigateTo(context: Context) {
            context.startActivity(Intent(context, AlbumRecyclerActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_recycler)

        recyclerView?.apply {
            layoutManager = LinearLayoutManager(this@AlbumRecyclerActivity)
            adapter = AlbumAdapter(albums, this@AlbumRecyclerActivity)
        }
    }

    override fun onAlbumClicked(album: Album?) {
        if (album != null) { // XXX
            AlbumDetailActivity.navigateTo(this, album.name, album.name)
        }
    }
}