package fr.esgi.firstfm.chart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.esgi.firstfm.R
import fr.esgi.firstfm.entity.model.Artist
import fr.esgi.firstfm.entity.model.Track

class ChartViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.chart_item, parent, false)),
    View.OnClickListener {
    interface OnChartClickedListener {
        fun onChartArtistClicked(artist: Artist?)
        fun onChartTrackClicked(track: Track?)
    }

    private var artist: Artist? = null
    private var track: Track? = null

    private var rankNumberTextView: TextView? = null
    private var chartItemNameTextView: TextView? = null
    private var chartItemPictureImageView: ImageView? = null
    private var onChartClickedListener: OnChartClickedListener? = null

    init {
        rankNumberTextView = itemView.findViewById(R.id.rankNumberTextView)
        chartItemNameTextView = itemView.findViewById(R.id.chartItemNameTextView)
        chartItemPictureImageView = itemView.findViewById(R.id.chartItemPictureImageView)
    }

    fun bindArtistValues(artist: Artist) {
        this.artist = artist
        chartItemNameTextView?.text = artist.name
        if (artist.spotifyImage.url != "") {
            Picasso.get()
                .load(artist.spotifyImage.url)
                .into(chartItemPictureImageView)
        }
    }

    fun bindTrackValues(track: Track) {
        this.track = track
        chartItemNameTextView?.text = track.name
        if (track.spotifyImage.url != "") {
            Picasso.get()
                .load(track.spotifyImage.url)
                .into(chartItemPictureImageView)
        }
    }

    fun bind(position: Int, onChartClickedListener: OnChartClickedListener) {
        this.onChartClickedListener = onChartClickedListener
        this.rankNumberTextView?.text = "$position"
        itemView.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        if (this.artist != null)
            onChartClickedListener?.onChartArtistClicked(artist)

        if (this.track != null)
            onChartClickedListener?.onChartTrackClicked(track)
    }
}