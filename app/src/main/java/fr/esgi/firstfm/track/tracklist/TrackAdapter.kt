package fr.esgi.firstfm.track.tracklist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.esgi.firstfm.lastfmapi.TrackResponse

class TrackAdapter(
    private val tracks: List<TrackResponse>,
    private val onTrackClickedListener: TrackViewHolder.OnTrackClickedListener
) :
    RecyclerView.Adapter<TrackViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return TrackViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = tracks.size

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = tracks[position]
        holder.bind(position + 1, track, onTrackClickedListener)
    }

}