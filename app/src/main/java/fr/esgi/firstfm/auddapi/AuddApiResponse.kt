package fr.esgi.firstfm.auddapi

import com.google.gson.annotations.SerializedName

data class AuddApiResponse(
    @SerializedName("status") val status: String,
    @SerializedName("result") val data: AuddApiData?
)
