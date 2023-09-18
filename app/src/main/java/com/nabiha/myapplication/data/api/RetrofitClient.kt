package com.nabiha.myapplication.data.api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val urlWifiRumah = "192.168.1.2"
    private const val urlHotspot = "192.168.200.47"

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://$urlWifiRumah:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }

}