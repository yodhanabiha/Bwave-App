package com.nabiha.myapplication.data.database

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.nabiha.myapplication.data.model.tokenModel.TokenModel
import com.nabiha.myapplication.data.model.user.DataUser
import kotlinx.coroutines.flow.map

class PreferenceDatastore(context: Context) {
    private val Context.dataStore : DataStore<Preferences> by preferencesDataStore("TOKEN_USER")
    private val pref = context.dataStore

    companion object{
        var tokenUser = stringPreferencesKey("TOKEN_USER")
    }

    suspend fun setToken(tokenModel: TokenModel){
        pref.edit {
            it[tokenUser] = tokenModel.remember_token
        }
    }

    fun getToken() = pref.data.map {
        TokenModel(it[tokenUser]?:"")
    }
}