package com.nabiha.myapplication.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.nabiha.myapplication.data.database.DatastoreManager
import com.nabiha.myapplication.data.database.PreferenceDatastore
import com.nabiha.myapplication.main.MainScreen
import com.nabiha.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                DatastoreManager.init(this)
                MainScreen()
            }
        }
    }
}

