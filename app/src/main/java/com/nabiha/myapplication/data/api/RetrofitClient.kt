package com.nabiha.myapplication.data.api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.16.47:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }
}