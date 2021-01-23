package fr.esgi.firstfm.auddapi

import com.google.gson.annotations.SerializedName

data class AuddApiData(
    @SerializedName("artist") val artist: String,
    @SerializedName("title") val title: String,
    @SerializedName("album") val album: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("label") val label: String,
    @SerializedName("timecode") val timecode: String,
    @SerializedName("song_link") val songLink: String
)
