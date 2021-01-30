package fr.esgi.firstfm.topFive

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.esgi.firstfm.R
import fr.esgi.firstfm.entity.Album
import fr.esgi.firstfm.entity.Artist
import fr.esgi.firstfm.entity.Track

class NominatedViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.nominated_item, parent, false)),
    View.OnClickListener {
    interface OnNominatedClickedListener {

        fun onNominatedAlbumClicked(album: Album?)
        fun onNominatedArtistClicked(artist: Artist?)
        fun onNominatedTrackClicked(track: Track?)
    }

    private var album: Album? = null
    private var artist: Artist? = null
    private var track: Track? = null

    private var rankNumberTextView: TextView? = null
    private var nominatedNameTextView: TextView? = null
    private var nominatedPictureImageView: ImageView? = null
    private var onNominatedClickedListener: OnNominatedClickedListener? = null

    init {
        rankNumberTextView = itemView.findViewById(R.id.rankNumberTextView)
        nominatedNameTextView = itemView.findViewById(R.id.nominatedNameTextView)
        nominatedPictureImageView = itemView.findViewById(R.id.nominatedPictureImageView)
    }

    fun bindAlbumValues(album: Album) {
        this.album = album
        nominatedNameTextView?.text = album.name
        Picasso.get()
            .load(album.image[album.image.size - 1].url)
            .into(nominatedPictureImageView)
    }

    fun bindArtistValues(artist: Artist) {
        this.artist = artist
        nominatedNameTextView?.text = artist.name
        if (artist.spotifyImages.isNotEmpty()) {
            Picasso.get()
                .load(artist.spotifyImages[0].url)
                .into(nominatedPictureImageView)
        }
    }

    fun bindTrackValues(track: Track) {
        this.track = track
        nominatedNameTextView?.text = track.name
        if (track.spotifyImages.isNotEmpty()) {
            Picasso.get()
                .load(track.spotifyImages[0].url)
                .into(nominatedPictureImageView)
        }
    }

    fun bind(position: Int, onNominatedClickedListener: OnNominatedClickedListener) {
        this.onNominatedClickedListener = onNominatedClickedListener
        this.rankNumberTextView?.text = "$position"
        itemView.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        if (this.album != null)
            onNominatedClickedListener?.onNominatedAlbumClicked(album)

        if (this.artist != null)
            onNominatedClickedListener?.onNominatedArtistClicked(artist)

        if (this.track != null)
            onNominatedClickedListener?.onNominatedTrackClicked(track)
    }
}