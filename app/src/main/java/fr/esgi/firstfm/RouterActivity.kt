package fr.esgi.firstfm

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.esgi.firstfm.ui.login.LoginActivity

class RouterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isLoggedIn()) {
            LoginActivity.navigateTo(this)
        } else {
            ProfileActivity.navigateTo(this)
        }
    }

    companion object {
        fun navigateTo(context: Context) {
            context.startActivity(Intent(context, RouterActivity::class.java))
        }
    }

    private fun isLoggedIn(): Boolean {
        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences("firstfm", MODE_PRIVATE)
        return sharedPreferences.contains("token") && sharedPreferences.contains("username")
    }
}
