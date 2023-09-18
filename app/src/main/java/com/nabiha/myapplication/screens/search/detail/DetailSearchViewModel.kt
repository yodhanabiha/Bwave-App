package com.nabiha.myapplication.screens.search.detail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.FirebaseStorage
import com.nabiha.myapplication.data.api.RetrofitClient
import com.nabiha.myapplication.data.database.DatastoreManager
import com.nabiha.myapplication.data.model.umkm.ListUmkm
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlin.math.log

class DetailSearchViewModel(private val id: Int) : ViewModel() {

    var umkm = mutableStateOf(ListUmkm())
    val retrofit = RetrofitClient.instance
    var refreshing by mutableStateOf(false)
    var isLoading by mutableStateOf(true)
    var isUpload by mutableStateOf(false)
    private val storage = FirebaseStorage.getInstance().reference

    init {
        viewModelScope.launch {
            viewUmkm()
            getUmkm()
        }
    }

    suspend fun viewUmkm(){
        try {

            val token = DatastoreManager.getPreferenceDatastore().getToken().firstOrNull() ?: return
            val header = mapOf(
                "Accept" to "application/json",
                "Authorization" to "Bearer ${token.remember_token}"
            )

            val response = retrofit.viewUmkm(id, header)

            if (response.isSuccessful){
                Log.d("VIEW", "viewUmkm: berhasil")
            }else{
                val errorMessage = response.errorBody()?.string()
                Log.d("GAGAL", "getUmkm: $errorMessage")
            }

        }catch (e: Exception){
            Log.d("ERR", "viewUmkm: $e")
        }
    }

    suspend fun getUmkm(){
        try {
            isLoading = true
            val token = DatastoreManager.getPreferenceDatastore().getToken().firstOrNull() ?: return
            val header = mapOf(
                "Accept" to "application/json",
                "Authorization" to "Bearer ${token.remember_token}"
            )
            val response = retrofit.getUmkm(id, header)

            if (response.isSuccessful) {
                val data = response.body()
                umkm.value = data ?: ListUmkm()
                val checkLike = retrofit.checkLike(id, header)
                if (checkLike.body() == 1){
                    umkm.value.like = true
                }
            } else {
                val errorMessage = response.errorBody()?.string()
                Log.d("GAGAL", "getUmkm: $errorMessage")
            }
            isLoading = false
        } catch (e: Exception) {
            Log.d("ERR", "getUmkm: $e")
        }
    }

    suspend fun likeUmkm() {
        try {
            val token = DatastoreManager.getPreferenceDatastore().getToken().firstOrNull() ?: return
            val header = mapOf(
                "Accept" to "application/json",
                "Authorization" to "Bearer ${token.remember_token}"
            )

            val response = retrofit.likeUmkm(id, header)

            if (response.isSuccessful) {
                umkm.value.like = true
            } else {
                val errorMessage = response.errorBody()?.string()
                Log.d("GAGAL", "likeUmkm: $errorMessage")
            }


        } catch (e: Exception) {
            Log.d("ERR", "likeUmkm: $e")
        }
    }

    suspend fun unlikeUmkm() {
        try {
            val token = DatastoreManager.getPreferenceDatastore().getToken().firstOrNull() ?: return
            val header = mapOf(
                "Accept" to "application/json",
                "Authorization" to "Bearer ${token.remember_token}"
            )

            val response = retrofit.unlikeUmkm(id, header)

            if (response.isSuccessful) {
                umkm.value.like = false
            } else {
                val errorMessage = response.errorBody()?.string()
                Log.d("GAGAL", "unlikeUmkm: $errorMessage")
            }


        } catch (e: Exception) {
            Log.d("ERR", "unlikeUmkm: $e")
        }
    }

}