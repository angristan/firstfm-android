package fr.esgi.firstfm.lastfmapi

import com.google.gson.annotations.SerializedName

data class TrackResponse(
    @SerializedName("name") val name: String,
    @SerializedName("duration") val duration: Long,
    @SerializedName("playcount") val playCount: Long,
    @SerializedName("listeners") val listeners: Long,
    @SerializedName("album") val album: Album,
    @SerializedName("toptags") val topTags: TopTags,

)
