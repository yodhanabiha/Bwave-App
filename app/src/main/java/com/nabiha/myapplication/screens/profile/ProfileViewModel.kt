package com.nabiha.myapplication.screens.profile

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.nabiha.myapplication.data.api.RetrofitClient
import com.nabiha.myapplication.data.database.DatastoreManager
import com.nabiha.myapplication.data.model.tokenModel.TokenModel
import com.nabiha.myapplication.data.model.user.DataUser
import com.nabiha.myapplication.navigation.BottomBarScreen
import com.nabiha.myapplication.navigation.Screens
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class ProfileViewModel() : ViewModel() {

    var user = mutableStateOf(DataUser())
    var refreshing by mutableStateOf(false)
    var isLoading by mutableStateOf(true)

    init {
        viewModelScope.launch {
            getDataUser()
            isLoading = false
        }
    }

    suspend fun refreshData() {
        try {
            refreshing = true
            delay(1_000L)
            getDataUser()
        } catch (e: Exception) {
            Log.d("ERR", "refreshData: $e")
        } finally {
            refreshing = false
        }
    }

    private suspend fun getDataUser() {
        try {
            val token = DatastoreManager.getPreferenceDatastore().getToken().firstOrNull() ?: return
            val response = RetrofitClient.instance.getUser(
                mapOf(
                    "Accept" to "application/json",
                    "Authorization" to "Bearer ${token.remember_token}"
                )
            )

            if (response.isSuccessful) {
                val userResponse = response.body()
                user.value = userResponse ?: DataUser()
            } else {
                val errorMessage = response.errorBody()?.string()
                Log.d("GAGAL", "getDataUser: $errorMessage")
            }

        } catch (e: Exception) {
            Log.d("ERR", "getDataUser: $e")
        }
    }

    suspend fun logout(navController : NavHostController, context: Context) {
        try {
            DatastoreManager.getPreferenceDatastore().setToken(TokenModel())
            navController.navigate(Screens.LoginScreen.route)
            Toast.makeText(context,"Account has been logged out", Toast.LENGTH_LONG).show()
        } catch (e : Exception) {
            Log.d("ERR", "logout: $e")
        }

    }


}