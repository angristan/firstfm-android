package fr.esgi.firstfm.objects

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Stats(
        @SerializedName("listeners") val listeners: Long,
        @SerializedName("playcount") val playCount: Long
): Serializable