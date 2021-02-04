package fr.esgi.firstfm.entity.result

import fr.esgi.firstfm.entity.model.Album

data class TopAlbumsResult(
    val success: List<Album>? = null,
    val error: Int? = null
)