package fr.esgi.firstfm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class GlobalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profile)
    }

    companion object {
        fun navigateTo(context: Context) {
            context.startActivity(Intent(context, GlobalActivity::class.java))
        }
    }
}