package fr.esgi.firstfm.entity

data class TopTracksResult(
    val success: List<Track>? = null,
    val error: Int? = null
)