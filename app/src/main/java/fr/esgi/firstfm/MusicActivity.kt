package fr.esgi.firstfm

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import fr.esgi.firstfm.lastfmapi.LastFmApi
import fr.esgi.firstfm.lastfmapi.LastFmApiTrackGetInfoResponse
import fr.esgi.firstfm.lastfmapi.Tag
import kotlinx.android.synthetic.main.activity_music.*
import org.jetbrains.anko.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MusicActivity : AppCompatActivity(), Callback<LastFmApiTrackGetInfoResponse> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)

        checkNeededPermissions()

        val title = intent.getStringExtra("title")
        val artist = intent.getStringExtra("artist")

        if (title != null && artist != null) {
            if (isNetworkConnected()) {
                LastFmApi.retrieveTrackInfo(artist, title, this)
            }
        }
    }

    override fun onFailure(call: Call<LastFmApiTrackGetInfoResponse>, t: Throwable) {
        loader.hide()
        MainActivity.navigateTo(this)
    }

    override fun onResponse(
        call: Call<LastFmApiTrackGetInfoResponse>,
        response: Response<LastFmApiTrackGetInfoResponse>
    ) {
        loader.hide()
        mainContainer.visibility = View.VISIBLE
        val track = response.body()?.track

        Picasso.get().load(track?.album?.images?.last()?.url).into(album_cover)
        trackTitle?.text = track?.name
        artist?.text = track?.album?.artist
        trackDurationValue?.text = convertDurationTrack(track?.duration)
        listenersNumber?.text = formatNumberToString(track?.listeners)
        scrobblesNumber?.text = formatNumberToString(track?.playCount)
        generateTagsInView(track?.topTags?.tags)
    }

    private fun convertDurationTrack(duration: Long?): String {
        if (duration != null) {
            val minutes =  duration / 1000 / 60
            val seconds =  duration / 1000 % 60

            return "${minutes}:${seconds}"
        }

        return ""
    }

    private fun formatNumberToString(listeners: Long?): String {
        if (listeners != null) {
            return when {
                listeners < 1000 -> {
                    "$listeners"
                }
                listeners in 1000..999999 -> {
                    val unit = listeners / 1000
                    val rest = listeners % 1000 / 100
                    "${unit}.${rest}K"
                }
                else -> {
                    val unit = listeners / 1000000
                    val rest = listeners % 1000000 / 1000
                    "${unit}.${rest}M"
                }
            }
        }

        return ""
    }

    private fun generateTagsInView(tags: List<Tag>?) {
        var counter = 0
        tags?.forEach { tag ->
            if (counter == 3) return

            val tagButton = Button(this)
            tagButton.isEnabled = false
            tagButton.text = tag.name
            tagButton.padding = 25

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(20, 5, 20, 5)
            tagButton.layoutParams = params

            val drawable = GradientDrawable()
            drawable.shape = GradientDrawable.RECTANGLE
            drawable.setStroke(5, ContextCompat.getColor(this, R.color.colorAccent))
            drawable.cornerRadius = 50.0F
            drawable.setColor(Color.TRANSPARENT)

            tagButton.background = drawable

            tagsContainer?.addView(tagButton)
            counter++
        }
    }

    private fun isNetworkConnected(): Boolean
    {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            val activeNetwork = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
            networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }
        else
        {
            true
            // TODO("VERSION.SDK_INT < M")
        }
    }

    private fun checkNeededPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
            != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE),
                0
            )
        }
    }

    companion object
    {
        fun navigateTo(context: Context)
        {
            context.startActivity(Intent(context, MusicActivity::class.java))
        }
    }
}
