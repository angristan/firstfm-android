package fr.esgi.firstfm.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
@Serializable
data class LoggedInUser(
    @SerialName("name")
    val userId: String,
    @SerialName("key")
    val token: String,
)

@Serializable
data class LoggedInUserSession(
    @SerialName("session")
    val user: LoggedInUser
)