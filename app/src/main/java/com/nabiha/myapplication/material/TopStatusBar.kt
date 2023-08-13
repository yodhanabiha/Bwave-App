package com.nabiha.myapplication.material

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun TopStatusBar(color: Color = Color.White, darkIcons: Boolean = true) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = color,
        darkIcons = darkIcons
    )


}

@Composable
fun BottomNavBar(color: Color = Color.White, darkIcons: Boolean = true) {

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()

    DisposableEffect(systemUiController, useDarkIcons) {
        systemUiController.setNavigationBarColor(
            color = color,
            darkIcons = useDarkIcons
        )
        onDispose {}
    }
}