package fr.esgi.firstfm.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.esgi.firstfm.R
import fr.esgi.firstfm.entity.model.Album
import fr.esgi.firstfm.entity.model.Artist
import fr.esgi.firstfm.entity.model.Track

class ProfileViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.profile_item, parent, false)),
    View.OnClickListener {
    interface OnProfileClickedListener {

        fun onProfileAlbumClicked(album: Album?)
        fun onProfileArtistClicked(artist: Artist?)
        fun onProfileTrackClicked(track: Track?)
    }

    private var album: Album? = null
    private var artist: Artist? = null
    private var track: Track? = null

    private var rankNumberTextView: TextView? = null
    private var profileItemNameTextView: TextView? = null
    private var profileItemPictureImageView: ImageView? = null
    private var onProfileClickedListener: OnProfileClickedListener? = null

    init {
        rankNumberTextView = itemView.findViewById(R.id.rankNumberTextView)
        profileItemNameTextView = itemView.findViewById(R.id.profileItemNameTextView)
        profileItemPictureImageView = itemView.findViewById(R.id.profileItemPictureImageView)
    }

    fun bindAlbumValues(album: Album) {
        this.album = album
        profileItemNameTextView?.text = album.name
        Picasso.get()
            .load(album.image[album.image.size - 1].url)
            .into(profileItemPictureImageView)
    }

    fun bindArtistValues(artist: Artist) {
        this.artist = artist
        profileItemNameTextView?.text = artist.name
        if (artist.spotifyImage.url != "") {
            Picasso.get()
                .load(artist.spotifyImage.url)
                .into(profileItemPictureImageView)
        }
    }

    fun bindTrackValues(track: Track) {
        this.track = track
        profileItemNameTextView?.text = track.name
        if (track.spotifyImage.url != "") {
            Picasso.get()
                .load(track.spotifyImage.url)
                .into(profileItemPictureImageView)
        }
    }

    fun bind(position: Int, onProfileClickedListener: OnProfileClickedListener) {
        this.onProfileClickedListener = onProfileClickedListener
        this.rankNumberTextView?.text = "$position"
        itemView.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        if (this.album != null)
            onProfileClickedListener?.onProfileAlbumClicked(album)

        if (this.artist != null)
            onProfileClickedListener?.onProfileArtistClicked(artist)

        if (this.track != null)
            onProfileClickedListener?.onProfileTrackClicked(track)
    }
}