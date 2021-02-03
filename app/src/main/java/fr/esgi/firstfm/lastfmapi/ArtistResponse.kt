package fr.esgi.firstfm.lastfmapi

import com.google.gson.annotations.SerializedName

data class ArtistResponse(
        @SerializedName("name") val name: String,
        @SerializedName("mbid") val mbId: String,
        @SerializedName("url") val url: String,
        @SerializedName("ontour") val onTour: String,
        @SerializedName("image") val images: List<Image>,
        @SerializedName("tags") val tags: TopTags,
        @SerializedName("similar") val similar: Artists,
        @SerializedName("stats.listeners") val listeners: String,
        @SerializedName("stats.playcount") val playCount: String
)
