package com.nabiha.myapplication.navigation

sealed class Screens(val route:String){
    object LoginScreen : Screens("login_screen")
    object RegisterScreen : Screens("register_screen")
    object UpdateProfileScreen : Screens("update_profile_screen")
    object DetailSearchScreen : Screens("detail_search_screen/{id}")
}
