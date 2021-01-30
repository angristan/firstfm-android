package fr.esgi.firstfm.entity

import fr.esgi.firstfm.objects.Artist

data class TopArtistsResult(
    val success: List<Artist>? = null,
    val error: Int? = null
)