package com.nabiha.myapplication.screens.profile.index

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.firebase.storage.FirebaseStorage
import com.nabiha.myapplication.data.api.RetrofitClient
import com.nabiha.myapplication.data.database.DatastoreManager
import com.nabiha.myapplication.data.model.tokenModel.TokenModel
import com.nabiha.myapplication.data.model.user.DataUser
import com.nabiha.myapplication.navigation.Screens
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProfileViewModel() : ViewModel() {

    var user = mutableStateOf(DataUser())
    var refreshing by mutableStateOf(false)
    var isLoading by mutableStateOf(true)
    var isUpload by mutableStateOf(false)
    private val storage = FirebaseStorage.getInstance().reference

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

    suspend fun updatePhoto(
        photoUri: Uri?,
        context: Context,
    ) {
        try {
            isUpload = true
            var downloadUrlPhoto : Uri? = photoUri
            val token = DatastoreManager.getPreferenceDatastore().getToken().firstOrNull() ?: return

            if (photoUri != null && photoUri.toString() != user.value.photoUri){
                val uploadData = storage.child("profileUser/${user.value.id}/photo")
                    .putFile(photoUri).await()
                downloadUrlPhoto = uploadData.storage.downloadUrl.await()
            }

            val data = user.value
            data.photoUri = downloadUrlPhoto.toString()

            val response = RetrofitClient.instance.updateUser(
                headers = mapOf(
                    "Accept" to "application/json",
                    "Authorization" to "Bearer ${token.remember_token}"
                ),
                data= data
            )

            Log.d("ISI DATA", "saveData: $data")

            isUpload = if (response.isSuccessful){
                Log.d("SUCCESS", "saveData: ${response.body()}")
                Toast.makeText(context, "data updated successfully", Toast.LENGTH_LONG).show()
                false
            }else{
                Log.d("ERROR", "saveData: ${response.errorBody()}")
                Toast.makeText(context, "data failed to update", Toast.LENGTH_LONG).show()
                false
            }

        }catch (e:Exception){
            Log.d("ERR", "saveData: $e")
            isUpload = false
        }
    }

    suspend fun updatePhotoBg(
        photoUri: Uri?,
        context: Context,
    ) {
        try {
            isUpload = true
            var downloadUrlPhoto : Uri? = photoUri
            val token = DatastoreManager.getPreferenceDatastore().getToken().firstOrNull() ?: return

            if (photoUri != null && photoUri.toString() != user.value.photoUri){
                val uploadData = storage.child("profileUser/${user.value.id}/background")
                    .putFile(photoUri).await()
                downloadUrlPhoto = uploadData.storage.downloadUrl.await()
            }

            val data = user.value
            data.photoUriBg = downloadUrlPhoto.toString()

            val response = RetrofitClient.instance.updateUser(
                headers = mapOf(
                    "Accept" to "application/json",
                    "Authorization" to "Bearer ${token.remember_token}"
                ),
                data= data
            )

            Log.d("ISI DATA", "saveData: $data")

            isUpload = if (response.isSuccessful){
                Log.d("SUCCESS", "saveData: ${response.body()}")
                Toast.makeText(context, "data updated successfully", Toast.LENGTH_LONG).show()
                false
            }else{
                Log.d("ERROR", "saveData: ${response.errorBody()}")
                Toast.makeText(context, "data failed to update", Toast.LENGTH_LONG).show()
                false
            }

        }catch (e:Exception){
            Log.d("ERR", "saveData: $e")
            isUpload = false
        }
    }


}