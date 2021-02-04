package fr.esgi.firstfm.auddapi

import fr.esgi.firstfm.BuildConfig
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

object AuddApi {
    private var auddApiServices: AuddApiServices? = null

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.audd.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        auddApiServices = retrofit.create(AuddApiServices::class.java)
    }

    fun recognizeAudio(file: File, callback: Callback<AuddApiResponse>) {
        val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
        val requestApiToken: RequestBody =
            BuildConfig.AUDD_API_TOKEN.toRequestBody(MultipartBody.FORM)

        val call = auddApiServices?.recognize(requestApiToken, body)

        call?.enqueue(callback)
    }
}