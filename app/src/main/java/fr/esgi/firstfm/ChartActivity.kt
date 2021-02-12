package fr.esgi.firstfm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import fr.esgi.firstfm.artist.ArtistDetailActivity
import fr.esgi.firstfm.chart.ChartAdapter
import fr.esgi.firstfm.chart.ChartViewHolder
import fr.esgi.firstfm.chart.ChartViewModel
import fr.esgi.firstfm.chart.ChartViewModelFactory
import fr.esgi.firstfm.entity.model.*
import kotlinx.android.synthetic.main.activity_album_detail.*
import kotlinx.android.synthetic.main.activity_charts.*
import kotlinx.android.synthetic.main.activity_login.*

class ChartActivity : AppCompatActivity(), ChartViewHolder.OnChartClickedListener {

    private lateinit var chartViewModel: ChartViewModel
    private lateinit var adapter: ChartAdapter
    private var artists: MutableList<Artist> = mutableListOf()
    private var tracks: MutableList<Track> = mutableListOf()
    private val resourcesToLoad =
        mutableMapOf("tracks" to true, "artists" to true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_charts)

        chartViewModel = ViewModelProviders.of(this, ChartViewModelFactory())
            .get(ChartViewModel::class.java)

        chartViewModel.getTopArtists(this)
        chartViewModel.getTopTracks(this)


        chartViewModel.topArtistsResult.observe(this@ChartActivity, Observer {
            val topArtistsResult = it ?: return@Observer

            if (loading != null) {
                chartLoader.visibility = View.GONE
            }
            if (topArtistsResult.error != null) {
                Toast.makeText(applicationContext, topArtistsResult.error, Toast.LENGTH_SHORT)
                    .show()

            }
            if (topArtistsResult.success != null) {
                this.artists = topArtistsResult.success as MutableList<Artist>
                this.adapter.updateArtists(artists)
                this.resourcesToLoad["artists"] = false
                this.updateLoader()
            }
        })

        chartViewModel.topTracksResult.observe(this@ChartActivity, Observer {
            val topTracksResult = it ?: return@Observer

            if (loading != null) {
                chartLoader.visibility = View.GONE
            }
            if (topTracksResult.error != null) {
                Toast.makeText(applicationContext, topTracksResult.error, Toast.LENGTH_SHORT)
                    .show()

            }
            if (topTracksResult.success != null) {
                this.tracks = topTracksResult.success as MutableList<Track>
                this.adapter.updateTracks(tracks)
                this.resourcesToLoad["tracks"] = false
                this.updateLoader()
            }
        })

        this.adapter = ChartAdapter(this.artists, this.tracks, this@ChartActivity)
        chartRecyclerView.layoutManager = LinearLayoutManager(this@ChartActivity)
        chartRecyclerView.adapter = this.adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun updateLoader() {
        val notLoaded = this.resourcesToLoad.filter { (_, value) -> value }
        if (notLoaded.isEmpty()) {
            chartLoader.visibility = View.GONE
            chartScrollView.visibility = View.VISIBLE
        }
    }

    companion object {
        fun navigateTo(context: Context) {
            context.startActivity(Intent(context, ChartActivity::class.java))
        }
    }

    override fun onChartArtistClicked(artist: Artist?) {
        if (artist != null) {
            ArtistDetailActivity.navigateTo(this, artist.name)
        }
    }

    override fun onChartTrackClicked(track: Track?) {
        if (track != null) {
            TrackActivity.navigateTo(this, track.name, track.artist.name)
        }
    }
}
