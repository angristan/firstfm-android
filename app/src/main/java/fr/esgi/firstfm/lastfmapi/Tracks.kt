package fr.esgi.firstfm.lastfmapi

import com.google.gson.annotations.SerializedName

data class Tracks(
    @SerializedName("track") val tracks: List<TrackResponse>
)
