package com.nabiha.myapplication.screens.auth.register

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.nabiha.myapplication.data.api.RetrofitClient
import com.nabiha.myapplication.data.database.PreferenceDatastore
import com.nabiha.myapplication.data.model.user.DataUser
import com.nabiha.myapplication.data.model.user.RegisterUser
import com.nabiha.myapplication.navigation.Screens
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    suspend fun addData(dataUser: RegisterUser, context: Context, navController: NavController) {
        try {
            val response = RetrofitClient.instance.register(dataUser)
            if (response.isSuccessful) {
                Toast.makeText(context, "account registered successfully", Toast.LENGTH_LONG).show()
                navController.navigate(Screens.LoginScreen.route)
                Log.d("SUCCESS", "addData: $response")
            } else {
                Toast.makeText(context, "registered email is already in use", Toast.LENGTH_LONG).show()
                Log.d("GAGAL", "addData: $response")
            }
        } catch (e: Exception) {
            Log.d("ERR", "addData: $e")
        }
    }


}