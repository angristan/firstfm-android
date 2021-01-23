package fr.esgi.firstfm

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import fr.esgi.firstfm.auddapi.AuddApi
import fr.esgi.firstfm.auddapi.AuddApiData
import fr.esgi.firstfm.auddapi.AuddApiResponse
import fr.esgi.firstfm.managers.FileManager
import kotlinx.android.synthetic.main.activity_music_scanner.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class MusicScannerActivity : AppCompatActivity(), View.OnClickListener, Callback<AuddApiResponse>{

    private var recorder: MediaRecorder? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_scanner)

        checkNeededPermissions()
        buttonWebservices?.setOnClickListener(this)
    }

    override fun onClick(view: View?)
    {
        if (isNetworkConnected()) {
            loader?.visibility = View.VISIBLE
            buttonWebservices?.visibility = View.GONE
            errorText?.visibility = View.GONE

            startRecording()
        }
        else
        {
            errorText?.text = "NO NETWORK !"
            errorText?.visibility = View.VISIBLE
        }
    }

    override fun onFailure(call: Call<AuddApiResponse>, t: Throwable)
    {
        loader?.visibility = View.GONE
        buttonWebservices?.visibility = View.VISIBLE
        errorText?.text = "ERROR: $t"
        errorText?.visibility = View.VISIBLE
    }

    override fun onResponse(call: Call<AuddApiResponse>, response: Response<AuddApiResponse>)
    {
        val musicInfo = response.body()?.data

        if (musicInfo == null) {
            loader?.visibility = View.GONE
            buttonWebservices?.visibility = View.VISIBLE
            Toast.makeText(this, "Audio not recognized, try to record in a quieter environment", Toast.LENGTH_LONG).show()
        } else {
            loader?.visibility = View.GONE

            buttonWebservices?.visibility = View.VISIBLE

            val dir = File(Environment.getExternalStorageDirectory().absolutePath + "/records")
            FileManager.delete(dir)

            navigateTo(this, musicInfo, MusicActivity::class.java)
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

    private fun setUpRecorder() {

        val dir = File(Environment.getExternalStorageDirectory().absolutePath + "/records")
        FileManager.create(dir)
        val path = "${dir.absolutePath}/record.mp3"

        recorder = MediaRecorder()

        recorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        recorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        recorder?.setOutputFile(path)
        recorder?.setMaxDuration(4000)
        recorder?.setOnInfoListener { mr, what, _ ->
            if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
                stopRecording(mr)
                val record = File(path)

                Log.d("recognizing:", "Start recognizing audio file")
                AuddApi.recognizeAudio(record, this)
            }
        }
    }

    private fun startRecording() {
        setUpRecorder()
        recorder?.prepare()
        recorder?.start()
    }

    private fun stopRecording(mr: MediaRecorder) {
        mr.stop()
        mr.reset()
        mr.release()
    }

    private fun navigateTo(context: Context, data: AuddApiData, activity: Class<MusicActivity>) {
        val intent = Intent(context, activity).apply {
            putExtra("title", data.title)
            putExtra("artist", data.artist)
            putExtra("album", data.album)
        }

        this.startActivity(intent)
    }

    private fun checkNeededPermissions() {
        println("Requesting permission")
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            )
            != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.INTERNET
            )
            != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_NETWORK_STATE
            )
            != PackageManager.PERMISSION_GRANTED) {
            println("Requesting permission")
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.RECORD_AUDIO
                ), 0
            )
        }
    }
}