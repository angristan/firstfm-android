package fr.esgi.firstfm.entity.result

import fr.esgi.firstfm.entity.model.Track

data class TopTracksResult(
    val success: List<Track>? = null,
    val error: Int? = null
)