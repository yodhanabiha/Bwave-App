package com.nabiha.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nabiha.myapplication.screens.auth.login.LoginScreen
import com.nabiha.myapplication.screens.auth.register.RegisterScreen
import com.nabiha.myapplication.screens.profile.index.ProfileScreen
import com.nabiha.myapplication.screens.profile.update.UpdateProfileScreen
import com.nabiha.myapplication.screens.search.detail.DetailSearchScreen
import com.nabiha.myapplication.screens.search.index.SearchScreen

@Composable
fun MainNavGraph(
    modifier: Modifier,
    navController: NavHostController,
    auth: Boolean?,
) {
    if (auth != null) {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = if (auth) BottomBarScreen.Home.route else Screens.LoginScreen.route
        ) {

            composable(BottomBarScreen.Profile.route) {
                ProfileScreen(navController = navController)
            }
            composable(BottomBarScreen.Home.route) {

            }
            composable(BottomBarScreen.Like.route) {

            }
            composable(BottomBarScreen.Search.route) {
                SearchScreen(navController)
            }
            composable(Screens.LoginScreen.route) {
                LoginScreen(navController)
            }
            composable(Screens.RegisterScreen.route) {
                RegisterScreen(navController)
            }
            composable(Screens.UpdateProfileScreen.route){
                UpdateProfileScreen(navController)
            }
            composable(Screens.DetailSearchScreen.route){
                DetailSearchScreen(
                    navController = navController,
                    id = it.arguments?.getString("id")!!.toInt()
                )
            }


        }
    }

}

