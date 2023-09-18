package com.nabiha.myapplication.screens.search.index

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.FirebaseStorage
import com.nabiha.myapplication.data.api.RetrofitClient
import com.nabiha.myapplication.data.database.DatastoreManager
import com.nabiha.myapplication.data.model.umkm.ListUmkm
import com.nabiha.myapplication.data.model.user.DataUser
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    var umkm = mutableStateListOf<ListUmkm>()
    val retrofit = RetrofitClient.instance
    var refreshing by mutableStateOf(false)
    var isLoading by mutableStateOf(true)
    var isUpload by mutableStateOf(false)
    private val storage = FirebaseStorage.getInstance().reference

    init {
        viewModelScope.launch {
            getUmkm()
        }
    }

    suspend fun likeUmkm(id: Int, idx: Int) {
        try {
            val token = DatastoreManager.getPreferenceDatastore().getToken().firstOrNull() ?: return
            val header = mapOf(
                "Accept" to "application/json",
                "Authorization" to "Bearer ${token.remember_token}"
            )

            val response = retrofit.likeUmkm(id, header)

            if (response.isSuccessful) {
                umkm[idx].like = true
            } else {
                val errorMessage = response.errorBody()?.string()
                Log.d("GAGAL", "likeUmkm: $errorMessage")
            }


        } catch (e: Exception) {
            Log.d("ERR", "likeUmkm: $e")
        }
    }

    suspend fun unlikeUmkm(id: Int, idx: Int) {
        try {
            val token = DatastoreManager.getPreferenceDatastore().getToken().firstOrNull() ?: return
            val header = mapOf(
                "Accept" to "application/json",
                "Authorization" to "Bearer ${token.remember_token}"
            )

            val response = retrofit.unlikeUmkm(id, header)

            if (response.isSuccessful) {
                umkm[idx].like = false
            } else {
                val errorMessage = response.errorBody()?.string()
                Log.d("GAGAL", "unlikeUmkm: $errorMessage")
            }


        } catch (e: Exception) {
            Log.d("ERR", "unlikeUmkm: $e")
        }
    }


    private suspend fun getUmkm() {
        try {
            val token = DatastoreManager.getPreferenceDatastore().getToken().firstOrNull() ?: return
            val header = mapOf(
                "Accept" to "application/json",
                "Authorization" to "Bearer ${token.remember_token}"
            )
            val response = retrofit.getUmkm(header)

            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    val umkmList = data.toMutableList()

                    umkmList.forEach { umkm ->
                        val checkLike = retrofit.checkLike(umkm.id, header)
                        if (checkLike.isSuccessful) {
                            val responseBody = checkLike.body()
                            if (responseBody == 1) {
                                val umkmToUpdate = umkmList.find { it.id == umkm.id }
                                umkmToUpdate?.like = true
                            }
                        }
                    }

                    umkm.clear()
                    umkm.addAll(umkmList)
                }
            } else {
                val errorMessage = response.errorBody()?.string()
                Log.d("GAGAL", "getDataUser: $errorMessage")
            }

        } catch (e: Exception) {
            Log.d("ERR", "getDataUser: $e")
        }
    }

}