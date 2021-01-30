package fr.esgi.firstfm.entity

import Track

data class TopTracksResult(
    val success: List<Track>? = null,
    val error: Int? = null
)