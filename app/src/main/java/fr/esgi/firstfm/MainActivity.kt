package fr.esgi.firstfm

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.esgi.firstfm.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isLoggedIn()) {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        startActivity(Intent(this, ProfileActivity::class.java))
    }

    private fun isLoggedIn(): Boolean {
        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences("firstfm", MODE_PRIVATE)
        return sharedPreferences.contains("token") && sharedPreferences.contains("username")
    }
}
