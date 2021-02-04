package fr.esgi.firstfm.album.albumList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import fr.esgi.firstfm.R
import fr.esgi.firstfm.lastfmapi.AlbumFromTop

class AlbumViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.album_item, parent, false)),
    View.OnClickListener {
    interface OnAlbumClickedListener {
        fun onAlbumClicked(album: AlbumFromTop?)
    }

    private var album: AlbumFromTop? = null

    private var loader: ContentLoadingProgressBar? = null
    private var rankNumberTextView: TextView? = null
    private var albumNameTextView: TextView? = null
    private var artistNameTextView: TextView? = null
    private var albumPictureImageView: ImageView? = null
    private var onAlbumClickedListener: OnAlbumClickedListener? = null

    init {
        loader = itemView.findViewById(R.id.loader)
        loader?.visibility = View.VISIBLE
        rankNumberTextView = itemView.findViewById(R.id.rankNumberTextView)
        albumNameTextView = itemView.findViewById(R.id.albumNameTextView)
        artistNameTextView = itemView.findViewById(R.id.artistNameTextView)
        albumPictureImageView = itemView.findViewById(R.id.albumPictureImageView)
    }

    fun bind(rank: Int, album: AlbumFromTop, onAlbumClickedListener: OnAlbumClickedListener) {
        this.album = album
        loader?.visibility = View.GONE
        this.onAlbumClickedListener = onAlbumClickedListener

        rankNumberTextView?.text = "$rank"
        albumNameTextView?.text = album.name
        artistNameTextView?.text = album.artist.name

        for (i in 2 downTo 0) {
            if (album.images[i].url != "") {
                Picasso.get()
                    ?.load(album.images[i].url)
                    ?.into(albumPictureImageView,
                        object : Callback {
                            override fun onSuccess() {}

                            override fun onError(e: Exception) {
                                albumPictureImageView?.setImageResource(R.drawable.default_album_picture)
                            }
                        }
                    )
                break
            }
        }
        itemView.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        onAlbumClickedListener?.onAlbumClicked(album)
    }
}
