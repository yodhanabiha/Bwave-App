package com.nabiha.myapplication.main

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.nabiha.myapplication.data.api.RetrofitClient
import com.nabiha.myapplication.data.database.DatastoreManager
import com.nabiha.myapplication.data.database.PreferenceDatastore
import kotlinx.coroutines.flow.firstOrNull

class MainViewModel : ViewModel() {

    var isLoading by mutableStateOf(false)
    suspend fun checkLogin(callback: (Boolean) -> Unit) {
        try {
            isLoading = true
            DatastoreManager.getPreferenceDatastore().getToken().firstOrNull()?.let { token ->
                val response =
                    RetrofitClient.instance.checkToken(
                        mapOf(
                            "Accept" to "application/json",
                            "Authorization" to "Bearer ${token.remember_token}"
                        )
                    )

                if (response.isSuccessful) {
                    callback(true)
                } else {
                    val errorMessage = response.errorBody()?.string()
                    Log.d("GAGAL", "login: $errorMessage")
                    callback(false)
                }
            }
            isLoading = false
        } catch (e: Exception) {
            Log.d("ERR", "getDataUser: $e")
            callback(false)
            isLoading = false
        }
    }

}