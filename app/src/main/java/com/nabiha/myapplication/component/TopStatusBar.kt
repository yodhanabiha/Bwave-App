package com.nabiha.myapplication.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.nabiha.myapplication.ui.theme.bg

@Composable
fun TopStatusBar(color: Color = bg, darkIcons: Boolean = true) {

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