package fr.esgi.firstfm.entity

data class TopArtistsResult(
    val success: List<Artist>? = null,
    val error: Int? = null
)