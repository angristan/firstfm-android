package fr.esgi.firstfm.entity.response

import fr.esgi.firstfm.entity.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse(
    @SerialName("user")
    val user: User
)