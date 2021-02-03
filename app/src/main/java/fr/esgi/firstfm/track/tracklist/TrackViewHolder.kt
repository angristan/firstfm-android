package fr.esgi.firstfm.track.tracklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.esgi.firstfm.R
import fr.esgi.firstfm.lastfmapi.TrackResponse

class TrackViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.track_item, parent, false)), View.OnClickListener {
    interface OnTrackClickedListener {
        fun onTrackClicked(track: TrackResponse?)
    }

    private var track: TrackResponse? = null

    private var rankNumberTextView: TextView? = null
    private var trackNameTextView: TextView? = null
    private var durationTextView: TextView? = null
    private var onTrackClickedListener: OnTrackClickedListener? = null

    init {
        rankNumberTextView = itemView.findViewById(R.id.rankNumberTextView)
        trackNameTextView = itemView.findViewById(R.id.trackNameTextView)
        durationTextView = itemView.findViewById(R.id.durationTextView)
    }

    fun bind(rank: Int, track: TrackResponse, onTrackClickedListener: OnTrackClickedListener) {
        this.track = track
        this.onTrackClickedListener = onTrackClickedListener
        rankNumberTextView?.text = "$rank"
        trackNameTextView?.text = track.name
        durationTextView?.text = convertDurationTrack(track.duration)
        itemView.setOnClickListener(this)
    }

    private fun convertDurationTrack(duration: Long?): String {
        if (duration != null) {
            val minutes = duration / 60
            val seconds = duration % 60
            if (seconds < 10)
                return "${minutes}:0${seconds}"

            return "${minutes}:${seconds}"
        }

        return ""
    }

    override fun onClick(v: View?) {
        onTrackClickedListener?.onTrackClicked(track)
    }
}