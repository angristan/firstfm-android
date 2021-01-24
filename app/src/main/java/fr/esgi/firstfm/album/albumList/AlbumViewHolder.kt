package fr.esgi.firstfm.album.albumList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.esgi.firstfm.R
import fr.esgi.firstfm.objects.Album

class AlbumViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.album_item, parent, false)), View.OnClickListener
{
    interface OnAlbumClickedListener
    {
        fun onAlbumClicked(album: Album?)
    }

    private var albumNameTextView: TextView? = null
    private var artistNameTextView: TextView? = null
    private var urlTextView: TextView? = null
    private var album: Album? = null
    private var onAlbumClickedListener: OnAlbumClickedListener? = null


    init
    {
        albumNameTextView = itemView.findViewById(R.id.albumNameTextView)
        artistNameTextView = itemView.findViewById(R.id.artistNameTextView)
        urlTextView = itemView.findViewById(R.id.urlTextView)
    }

    fun bind(album: Album, onAlbumClickedListener: OnAlbumClickedListener)
    {
        this.album = album
        this.onAlbumClickedListener = onAlbumClickedListener
        albumNameTextView?.text = album.album
        artistNameTextView?.text = album.artist
        urlTextView?.text = album.url

        itemView.setOnClickListener(this)
    }

    override fun onClick(p0: View?)
    {
        onAlbumClickedListener?.onAlbumClicked(album)
    }
}