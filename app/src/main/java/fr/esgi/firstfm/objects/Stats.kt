package fr.esgi.firstfm.objects

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Stats(
        @SerializedName("listeners") val listeners: String,
        @SerializedName("playcount") val playCount: String
): Serializable