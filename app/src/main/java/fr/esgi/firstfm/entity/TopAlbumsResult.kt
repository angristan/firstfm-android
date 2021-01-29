package fr.esgi.firstfm.entity

import fr.esgi.firstfm.objects.Album

data class TopAlbumsResult(
    val success: List<Album>? = null,
    val error: Int? = null
)