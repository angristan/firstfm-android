package fr.esgi.firstfm

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.esgi.firstfm.album.albumList.AlbumRecyclerActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonGoToAlbumActivity?.setOnClickListener {
            AlbumRecyclerActivity.navigateTo(this)
        }
    }

    companion object
    {
        fun navigateTo(context: Context)
        {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
}