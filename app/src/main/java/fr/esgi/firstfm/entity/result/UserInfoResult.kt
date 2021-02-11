package fr.esgi.firstfm.entity.result

import fr.esgi.firstfm.entity.model.User

data class UserInfoResult(
    val success: User? = null,
    val error: Int? = null
)