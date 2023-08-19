package com.nabiha.myapplication.data.api

import com.nabiha.myapplication.data.model.response.ResponseModel
import com.nabiha.myapplication.data.model.user.DataUser
import com.nabiha.myapplication.data.model.user.LoginUser
import com.nabiha.myapplication.data.model.user.RegisterUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ApiService {
    @POST("/api/register")
    @Headers("Accept: application/json")
    suspend fun register(
        @Body data: RegisterUser
    ): Response<RegisterUser>

    @POST("/api/login")
    @Headers("Accept: application/json")
    suspend fun login(
        @Body data: LoginUser
    ): Response<ResponseModel>

    @GET("/api/check-token")
    suspend fun checkToken(
        @HeaderMap headers : Map<String, String>
    ): Response<Void>

    @GET("/api/user")
    suspend fun getUser(
        @HeaderMap headers : Map<String, String>
    ): Response<DataUser>

    @PATCH("/api/user/update")
    suspend fun updateUser(
        @HeaderMap headers: Map<String, String>,
        @Body data:DataUser
    ):Response<Void>

}