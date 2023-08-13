package com.nabiha.myapplication.data.model.user

data class DataUser(
    val id: Int = 0,
    val username: String = "",
    val number: String = "",
    val photoUri: String? = null,
    val email: String = "",
    val photoUriBg: String? = null
)
