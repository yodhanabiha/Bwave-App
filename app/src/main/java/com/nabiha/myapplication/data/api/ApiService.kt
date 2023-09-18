package com.nabiha.myapplication.data.api

import com.nabiha.myapplication.data.model.response.ResponseModel
import com.nabiha.myapplication.data.model.umkm.ListUmkm
import com.nabiha.myapplication.data.model.user.DataUser
import com.nabiha.myapplication.data.model.user.LoginUser
import com.nabiha.myapplication.data.model.user.RegisterUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

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

    @GET("/api/getumkm")
    suspend fun getUmkm(
        @HeaderMap headers: Map<String, String>
    ): Response<List<ListUmkm>>

    @GET("/api/getumkm/{id}")
    suspend fun getUmkm(
        @Path("id") id: Int,
        @HeaderMap headers: Map<String, String>,
    ): Response<ListUmkm>

    @POST("/api/likeumkm/{id}")
    suspend fun likeUmkm(
        @Path("id") id: Int,
        @HeaderMap headers: Map<String, String>,
    ): Response<Void>

    @DELETE("/api/unlikeumkm/{id}")
    suspend fun unlikeUmkm(
        @Path("id") id: Int,
        @HeaderMap headers: Map<String, String>,
    ): Response<Void>

    @GET("/api/checklike/{id}")
    suspend fun checkLike(
        @Path("id") id: Int,
        @HeaderMap headers: Map<String, String>,
    ): Response<Int>

    @POST("/api/viewumkm/{id}")
    suspend fun viewUmkm(
        @Path("id") id: Int,
        @HeaderMap header: Map<String, String>,
    ): Response<Void>


}