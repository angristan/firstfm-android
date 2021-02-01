package fr.esgi.firstfm.album.albumList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.esgi.firstfm.R
import fr.esgi.firstfm.objects.Album

class AlbumViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.album_item, parent, false)), View.OnClickListener {
    interface OnAlbumClickedListener {
        fun onAlbumClicked(album: Album?)
    }

    private var album: Album? = null

    private var rankNumberTextView: TextView? = null
    private var albumNameTextView: TextView? = null
    private var artistNameTextView: TextView? = null
    private var albumPictureImageView: ImageView? = null
    private var onAlbumClickedListener: OnAlbumClickedListener? = null

    init {
        rankNumberTextView = itemView.findViewById(R.id.rankNumberTextView)
        albumNameTextView = itemView.findViewById(R.id.albumNameTextView)
        artistNameTextView = itemView.findViewById(R.id.artistNameTextView)
        albumPictureImageView = itemView.findViewById(R.id.albumPictureImageView)
    }

    fun bind(rank: Int, album: Album, onAlbumClickedListener: OnAlbumClickedListener) {
        this.album = album
        this.onAlbumClickedListener = onAlbumClickedListener
        rankNumberTextView?.text = "$rank"
        albumNameTextView?.text = album.name
        artistNameTextView?.text = album.artist

        Picasso.get()
                .load(album.url)
                .into(albumPictureImageView)

        itemView.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        onAlbumClickedListener?.onAlbumClicked(album)
    }
}