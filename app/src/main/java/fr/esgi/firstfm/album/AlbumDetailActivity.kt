package fr.esgi.firstfm.album

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.esgi.firstfm.R
import kotlinx.android.synthetic.main.activity_album_detail.*

class AlbumDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_detail)

        val receivedValue = intent?.getStringExtra("albumName")
        albumDetailName?.text = "$receivedValue"
    }

    companion object
    {
        private val PARAM1: String = "albumName"
        private val PARAM2: String = "artistName"

        fun navigateTo(context: Context, param1: String, param2: String)
        {
            val intent = Intent(context, AlbumDetailActivity::class.java).apply {
                putExtra(PARAM1, param1)
                putExtra(PARAM2, param2)
            }
            context.startActivity(intent)
        }
    }
}