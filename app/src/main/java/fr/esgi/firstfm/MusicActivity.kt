package fr.esgi.firstfm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MusicActivity : AppCompatActivity() {

    lateinit var title: TextView
    lateinit var artist: TextView
    lateinit var album: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)

        title = findViewById(R.id.titleTextView)
        artist = findViewById(R.id.artistTextView)
        album = findViewById(R.id.albumTextView)

        title.text = intent.getStringExtra("title")
        artist.text = intent.getStringExtra("artist")
        album.text = intent.getStringExtra("album")

    }
}