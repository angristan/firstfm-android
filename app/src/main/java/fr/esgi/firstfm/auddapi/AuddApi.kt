package fr.esgi.firstfm.auddapi

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File


object AuddApi {
    private const val apiTokenKey: String = "81ef1dd93aeefb921110ab2458e72823"
    private var auddApiServices: AuddApiServices? = null

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.audd.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        auddApiServices = retrofit.create(AuddApiServices::class.java)
    }

    fun recognizeAudio(file: File, callback: Callback<AuddApiResponse>) {
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
        val requestApiToken: RequestBody = RequestBody.create(MultipartBody.FORM, apiTokenKey)

        val call = auddApiServices?.recognize(requestApiToken, body)

        call?.enqueue(callback)
    }
}