package com.nabiha.myapplication.data.model.umkm

data class ListUmkm(
    val id: Int = 0,
    val image: String = "",
    val title: String = "",
    var like: Boolean = false,
    val star: Double = 0.0,
    val view: Int = 0,
    val review: Int = 0,
    val rating: Int = 0,
    val description: String = "",
    val location: String = "",
    val time_open: String = "",
    val time_close: String = ""
)
