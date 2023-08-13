package com.nabiha.myapplication.navigation

import com.nabiha.myapplication.R

sealed class BottomBarScreen(
    val route : String,
    val title : String,
    val icon : Int
){
    object Home:BottomBarScreen(
        route = "home",
        title = "home",
        icon = R.drawable.icn_home
    )
    object Search:BottomBarScreen(
        route = "search",
        title = "search",
        icon = R.drawable.icn_search
    )
    object Like:BottomBarScreen(
        route = "like",
        title = "like",
        icon = R.drawable.icn_love
    )
    object Profile:BottomBarScreen(
        route = "profile",
        title = "profile",
        icon = R.drawable.icn_user
    )
}
