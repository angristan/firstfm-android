package fr.esgi.firstfm.topFive

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.esgi.firstfm.objects.Album
import fr.esgi.firstfm.objects.Artist
import fr.esgi.firstfm.objects.Track


class TopFiveAdapter(
    private val albums: MutableList<Album>,
    private val artists: MutableList<Artist>,
    private val tracks: MutableList<Track>,
    private val onNominatedClickedListener: NominatedViewHolder.OnNominatedClickedListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_TITLE = 1
    private val TYPE_NOMINATED = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_TITLE) {
            val inflater = LayoutInflater.from(parent.context)
            TitleViewHolder(inflater, parent)
        } else {
            val inflater = LayoutInflater.from(parent.context)
            NominatedViewHolder(inflater, parent)
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

    override fun getItemCount(): Int {
        return 3 + albums.size + artists.size + tracks.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 || position == 6 || position == 12) {
            TYPE_TITLE
        } else {
            TYPE_NOMINATED
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
                    (holder as NominatedViewHolder).bindAlbumValues(album)
                    holder.bind(position, onNominatedClickedListener)
                }
            }
            6 -> {
                (holder as TitleViewHolder).bind("Top Five Artist")
            }
            in 7..11 -> {
                if (artists.size > position - 7) {
                    val artist = artists[position - 7]
                    (holder as NominatedViewHolder).bindArtistValues(artist)
                    holder.bind(position - 6, onNominatedClickedListener)
                }
            }
            12 -> {
                (holder as TitleViewHolder).bind("Top Five Track")
            }
            in 13..18 -> {
                if (tracks.size > position - 13) {
                    val track = tracks[position - 13]
                    (holder as NominatedViewHolder).bindTrackValues(track)
                    holder.bind(position - 12, onNominatedClickedListener)
                }
            }
        }
    }
}