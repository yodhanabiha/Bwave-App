package com.nabiha.myapplication.screens.auth.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.nabiha.myapplication.data.api.RetrofitClient
import com.nabiha.myapplication.data.database.DatastoreManager
import com.nabiha.myapplication.data.database.PreferenceDatastore
import com.nabiha.myapplication.data.model.tokenModel.TokenModel
import com.nabiha.myapplication.data.model.user.DataUser
import com.nabiha.myapplication.data.model.user.LoginUser
import com.nabiha.myapplication.navigation.BottomBarScreen

class LoginViewModel : ViewModel() {

    suspend fun login(loginUser: LoginUser, context: Context, navController: NavHostController){
        try {
            val response = RetrofitClient.instance.login(loginUser)
            if (response.isSuccessful) {
                val loginResponse = response.body()
                if (loginResponse?.success == true) {
                    Toast.makeText(context, "Login Berhasil", Toast.LENGTH_LONG).show()
                    Log.d("SUCCESS", "login: ${loginResponse.message}")
                    val token = TokenModel(loginResponse.data.toString())
                    DatastoreManager.getPreferenceDatastore().setToken(token)
                    Log.d("TOKEN", "login: ${loginResponse.data.toString()}")
                    navController.navigate(BottomBarScreen.Profile.route)

                } else {
                    val errorMessage = loginResponse?.message
                    Toast.makeText(context, "Gagal Login: $errorMessage", Toast.LENGTH_LONG).show()
                    Log.d("GAGAL", "login: $errorMessage")
                }
            } else {
                val errorMessage = response.errorBody()?.string()
                Toast.makeText(context, "account doesn't exist", Toast.LENGTH_LONG).show()
                Log.d("GAGAL", "login: $errorMessage")
            }
        } catch (e: Exception) {
            Log.d("ERR", "login: $e")
        }
    }

}