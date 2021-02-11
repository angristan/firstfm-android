package fr.esgi.firstfm.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.esgi.firstfm.entity.model.Album
import fr.esgi.firstfm.entity.model.Artist
import fr.esgi.firstfm.entity.model.Track


class ProfileAdapter(
    private val albums: MutableList<Album>,
    private val artists: MutableList<Artist>,
    private val tracks: MutableList<Track>,
    private val onProfileClickedListener: ProfileViewHolder.OnProfileClickedListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_TITLE = 1
    private val TYPE_ITEM = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_TITLE) {
            val inflater = LayoutInflater.from(parent.context)
            TitleViewHolder(inflater, parent)
        } else {
            val inflater = LayoutInflater.from(parent.context)
            ProfileViewHolder(inflater, parent)
        }
    }

    fun updateAlbums(newAlbums: List<Album>) {
        with(albums) {
            clear()
            addAll(newAlbums)
        }
        notifyDataSetChanged()
    }

    fun updateArtists(newArtists: List<Artist>) {
        with(artists) {
            clear()
            addAll(newArtists)
        }
        notifyDataSetChanged()
    }

    fun updateTracks(newTracks: List<Track>) {
        with(tracks) {
            clear()
            addAll(newTracks)
        }
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return 3 + albums.size + artists.size + tracks.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 || position == 6 || position == 12) {
            TYPE_TITLE
        } else {
            TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (position) {
            0 -> {
                (holder as TitleViewHolder).bind("Top Five Album")
            }
            in 1..5 -> {
                if (albums.size > position - 1) {
                    val album = albums[position - 1]
                    (holder as ProfileViewHolder).bindAlbumValues(album)
                    holder.bind(position, onProfileClickedListener)
                }
            }
            6 -> {
                (holder as TitleViewHolder).bind("Top Five Artist")
            }
            in 7..11 -> {
                if (artists.size > position - 7) {
                    val artist = artists[position - 7]
                    (holder as ProfileViewHolder).bindArtistValues(artist)
                    holder.bind(position - 6, onProfileClickedListener)
                }
            }
            12 -> {
                (holder as TitleViewHolder).bind("Top Five Track")
            }
            in 13..18 -> {
                if (tracks.size > position - 13) {
                    val track = tracks[position - 13]
                    (holder as ProfileViewHolder).bindTrackValues(track)
                    holder.bind(position - 12, onProfileClickedListener)
                }
            }
        }
    }
}