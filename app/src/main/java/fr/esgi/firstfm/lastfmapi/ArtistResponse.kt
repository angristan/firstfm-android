package fr.esgi.firstfm.lastfmapi

import com.google.gson.annotations.SerializedName
import fr.esgi.firstfm.objects.Stats

data class ArtistResponse(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
    @SerializedName("ontour") val onTour: String,
    @SerializedName("image") val images: List<Image>,
    @SerializedName("tags") val tags: TopTags,
//        @SerializedName("similar") val similar: Artists,
    @SerializedName("stats") val stats: Stats
)
