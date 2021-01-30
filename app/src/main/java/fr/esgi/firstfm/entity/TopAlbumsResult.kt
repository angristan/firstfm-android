package fr.esgi.firstfm.entity

data class TopAlbumsResult(
    val success: List<Album>? = null,
    val error: Int? = null
)