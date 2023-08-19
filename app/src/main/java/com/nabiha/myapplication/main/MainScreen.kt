package com.nabiha.myapplication.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nabiha.myapplication.data.database.PreferenceDatastore
import com.nabiha.myapplication.navigation.BottomBarScreen
import com.nabiha.myapplication.navigation.MainNavGraph
import com.nabiha.myapplication.ui.theme.primary

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel = viewModel()
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    var authentication: Boolean? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(Unit) {
        mainViewModel.checkLogin { isLoggedIn ->
            authentication = isLoggedIn
        }
    }

    Scaffold(bottomBar = { BottomBar(navController = navController) }) { innerPadding ->
        MainNavGraph(
            modifier = Modifier
                .fillMaxSize()
                //.padding(innerPadding)
                .consumeWindowInsets(innerPadding),
            navController = navController,
            auth = authentication,
        )
    }

}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Search,
        BottomBarScreen.Like,
        BottomBarScreen.Profile,
    )
    val routes = screens.map {
        it.route
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    AnimatedVisibility(visible = routes.any { it == currentDestination?.route }) {
        NavigationBar(modifier = Modifier.fillMaxWidth(), containerColor = Color.White) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}


@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route)
        },
        label = {
            Text(text = screen.title, style = MaterialTheme.typography.bodyMedium)
        },
        icon = {
            Icon(
                painter = painterResource(id = screen.icon),
                contentDescription = "",
                modifier = Modifier.size(28.dp)
            )
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = primary,
            selectedTextColor = primary,
            indicatorColor = Color.White,
            unselectedIconColor = Color.Black,
            unselectedTextColor = Color.Black
        )
    )
}
