package fr.esgi.firstfm.entity.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Image(
    @SerialName("size") val size: String = "",
    @SerialName("#text") val url: String = ""
)