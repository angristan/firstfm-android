package fr.esgi.firstfm.lastfmapi

import com.google.gson.annotations.SerializedName

data class TopTags(
    @SerializedName("tag") val tags: List<Tag>
)
