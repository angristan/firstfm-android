package fr.esgi.firstfm.chart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.esgi.firstfm.entity.model.Artist
import fr.esgi.firstfm.entity.model.Track


class ChartAdapter(
    private val artists: MutableList<Artist>,
    private val tracks: MutableList<Track>,
    private val onChartClickedListener: ChartViewHolder.OnChartClickedListener
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
            ChartViewHolder(inflater, parent)
        }
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
        return 2 + artists.size + tracks.size
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
                (holder as TitleViewHolder).bind("Top Five Artists")
            }
            in 1..5 -> {
                if (artists.size > position - 1) {
                    val artist = artists[position - 1]
                    (holder as ChartViewHolder).bindArtistValues(artist)
                    holder.bind(position, onChartClickedListener)
                }
            }
            6 -> {
                (holder as TitleViewHolder).bind("Top Five Tracks")
            }
            in 7..11 -> {
                if (tracks.size > position - 7) {
                    val track = tracks[position - 7]
                    (holder as ChartViewHolder).bindTrackValues(track)
                    holder.bind(position - 6, onChartClickedListener)
                }
            }
        }
    }
}