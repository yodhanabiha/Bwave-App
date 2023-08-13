package com.nabiha.myapplication.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.nabiha.myapplication.R
import com.nabiha.myapplication.data.database.PreferenceDatastore
import com.nabiha.myapplication.material.AnimationLoading
import com.nabiha.myapplication.material.TopStatusBar
import com.nabiha.myapplication.navigation.Screens
import com.nabiha.myapplication.ui.theme.bg
import com.nabiha.myapplication.ui.theme.lightGray
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(
    navController: NavHostController,
    profileViewModel: ProfileViewModel = viewModel()
) {

    TopStatusBar(color = Color.Transparent, darkIcons = false)
    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current
    val pullRefreshState = rememberPullRefreshState(
        refreshing = profileViewModel.refreshing,
        onRefresh = {
            coroutine.launch {
                profileViewModel.refreshData()
            }
        }
    )

    val user = profileViewModel.user.value
    val isloading = profileViewModel.isLoading

    if (isloading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 200.dp),
            contentAlignment = Alignment.Center
        ) {
            AnimationLoading(modifier = Modifier.wrapContentSize())
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
                .background(color = bg)
        ) {
            LazyColumn(
                modifier = Modifier
                    .imePadding()
                    .fillMaxSize(),
            ) {
                item {
                    Box {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(366.dp),
                            colors = CardDefaults.cardColors(Color(0xBF3A6DB8)),
                            shape = RectangleShape,
                        ) {
                            if (user.photoUriBg != null) {
                                Image(
                                    painter = rememberAsyncImagePainter(model = user.photoUriBg),
                                    contentDescription = null,
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 26.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 64.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stringResource(id = R.string.profile),
                                    style = MaterialTheme.typography.titleLarge,
                                    color = Color.White
                                )
                                Row {
                                    Spacer(modifier = Modifier.weight(1f))
                                    Icon(
                                        painter = painterResource(id = R.drawable.three_dot),
                                        contentDescription = "",
                                        tint = Color.White
                                    )
                                }

                            }

                            Card(
                                modifier = Modifier
                                    .padding(top = 24.dp)
                                    .size(110.dp),
                                colors = CardDefaults.cardColors(lightGray),
                                shape = CircleShape,
                                elevation = CardDefaults.cardElevation(4.dp)
                            ) {
                                if (user.photoUri != null) {
                                    Image(
                                        painter = rememberAsyncImagePainter(model = user.photoUri),
                                        contentDescription = "",
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }

                            Text(
                                text = user.username,
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White,
                                fontSize = 26.sp,
                                modifier = Modifier.padding(top = 27.dp)
                            )
                            Text(
                                text = user.email,
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                modifier = Modifier.padding(top = 2.dp)
                            )

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 31.dp),
                                colors = CardDefaults.cardColors(Color.White),
                                elevation = CardDefaults.cardElevation(4.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp)
                                ) {

                                    //account
                                    ProfileFiture(
                                        icon = R.drawable.lock,
                                        modifier = Modifier.padding(top = 28.dp, bottom = 24.dp),
                                        backgroundIconColor = Color(0xFFFF7E07),
                                        text = stringResource(id = R.string.account_scrty)
                                    ) {

                                    }

                                    //logout
                                    ProfileFiture(
                                        icon = R.drawable.logout,
                                        modifier = Modifier.padding(bottom = 26.dp),
                                        backgroundIconColor = Color(0xFFFF3D00),
                                        text = stringResource(id = R.string.logout)
                                    ) {
                                        coroutine.launch {
                                            profileViewModel.logout(navController, context)
                                        }
                                    }

                                }
                            }


                        }
                    }

                }
            }

            PullRefreshIndicator(
                refreshing = profileViewModel.refreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }

    }


}

@Composable
fun ProfileFiture(
    icon: Int,
    backgroundIconColor: Color,
    modifier: Modifier,
    text: String,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable {
            onClick.invoke()
        }
    ) {

        Card(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(backgroundIconColor),
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "",
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize(),
                tint = Color.White
            )
        }

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.arrow_right),
            contentDescription = "",
        )
    }
}