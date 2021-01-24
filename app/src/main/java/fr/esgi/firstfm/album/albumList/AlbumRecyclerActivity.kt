package fr.esgi.firstfm.album.albumList

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import fr.esgi.firstfm.R
import fr.esgi.firstfm.album.AlbumDetailActivity
import fr.esgi.firstfm.objects.Album
import kotlinx.android.synthetic.main.activity_album_recycler.*

class AlbumRecyclerActivity : AppCompatActivity(), AlbumViewHolder.OnAlbumClickedListener
{
    private val albums = listOf(
        Album("John", "Cena", "Lorem ipsum"),
        Album("Homer", "Simpson", "Lorem ipsum"),
        Album("Bob", "Dylan", "Lorem ipsum"),
        Album("Rick", "Morty", "Lorem ipsum"),
        Album("Marcel", "Pagnol", "Lorem ipsum"),
        Album("Asterix", "& Obelix", "Lorem ipsum"),
        Album("Kaaris", "Sevran", "Lorem ipsum"),
        Album("Provençal", "Le Gaullois", "Lorem ipsum")
    )

    companion object
    {
        private val PARAM1: String = "param1"
        private val PARAM2: String = "param2"

        fun navigateTo(context: Context, param1: String, param2: Int)
        {
            val intent = Intent(context, AlbumRecyclerActivity::class.java).apply {
                putExtra(PARAM1, param1)
                putExtra(PARAM2, param2)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_recycler)

        recyclerView?.apply {
            layoutManager = LinearLayoutManager(this@AlbumRecyclerActivity)
            adapter = AlbumAdapter(albums, this@AlbumRecyclerActivity)
        }
    }

    override fun onAlbumClicked(album: Album?)
    {
        if (album != null) {
            AlbumDetailActivity.navigateTo(this, album.album, album.artist)
        }
    }
}