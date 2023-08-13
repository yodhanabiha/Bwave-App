package com.nabiha.myapplication.navigation

sealed class Screens(val route:String){
    object LoginScreen : Screens("login_screen")
    object RegisterScreen : Screens("register_screen")
}
