package fr.esgi.firstfm.lastfmapi

import com.google.gson.annotations.SerializedName

data class Artists(
    @SerializedName("artist") val tags: List<ArtistResponse>
)
