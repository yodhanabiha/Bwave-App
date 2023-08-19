package com.nabiha.myapplication.data.model.user

data class DataUser(
    val id: Int = 0,
    val username: String = "",
    val number: String = "",
    var photoUri: String? = null,
    val email: String = "",
    var photoUriBg: String? = null,
    val bio: String? = null
)
