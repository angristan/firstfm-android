package fr.esgi.firstfm.entity.result

import fr.esgi.firstfm.entity.model.Artist

data class TopArtistsResult(
    val success: List<Artist>? = null,
    val error: Int? = null
)