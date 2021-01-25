package fr.esgi.firstfm.auddapi

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AuddApiServices {

    @Multipart
    @POST("/")
    fun recognize(
        @Part("api_token") apiKey: RequestBody,
        @Part file: MultipartBody.Part
    ): Call<AuddApiResponse>
}