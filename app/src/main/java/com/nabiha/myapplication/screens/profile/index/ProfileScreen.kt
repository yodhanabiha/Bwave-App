package com.nabiha.myapplication.screens.profile.index

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.nabiha.myapplication.component.AnimationLoading
import com.nabiha.myapplication.component.ButtonAnimationIcon
import com.nabiha.myapplication.component.TopStatusBar
import com.nabiha.myapplication.navigation.Screens
import com.nabiha.myapplication.ui.theme.bg
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

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )

    val user = profileViewModel.user.value
    val isLoading = profileViewModel.isLoading
    val isUpload = profileViewModel.isUpload

    if (isUpload) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 200.dp),
            contentAlignment = Alignment.Center
        ) {
            AnimationLoading(modifier = Modifier.wrapContentSize())
        }
    } else {
        var photoUri:  Uri? by remember { mutableStateOf(null) }
        val launcherPhoto =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
                photoUri = uri
            }

        var photoUriBg:  Uri? by remember { mutableStateOf(null) }
        val launcherPhotoBg =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
                photoUriBg = uri
            }


        ModalBottomSheetLayout(
            sheetContent = {
                Column(modifier = Modifier.padding(start = 20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(Color(0xFFB8B6B6)),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .padding(bottom = 58.dp, top = 12.dp)
                                .width(86.dp)
                                .height(8.dp)
                        ) {

                        }
                    }
                    BottomSheet(
                        icon = R.drawable.icn_image,
                        modifier = Modifier.padding(bottom = 24.dp),
                        backgroundIconColor = Color(0xFFBA8AD0),
                        text = stringResource(id = R.string.edit_bg)
                    ) {
                        launcherPhotoBg.launch("image/*")
                    }

                    BottomSheet(
                        icon = R.drawable.icn_photo_user,
                        modifier = Modifier.padding(bottom = 24.dp),
                        backgroundIconColor = Color(0xFF93A2D8),
                        text = stringResource(id = R.string.edit_photo)
                    ) {
                        launcherPhoto.launch("image/*")
                    }

                    LaunchedEffect(photoUri) {
                        photoUri?.let { uri ->
                            coroutine.launch {
                                profileViewModel.updatePhoto(uri, context)
                            }
                        }
                    }

                    LaunchedEffect(photoUriBg){
                        photoUriBg?.let { uri ->
                            coroutine.launch {
                                profileViewModel.updatePhotoBg(uri,context)

                            }
                        }
                    }

                    BottomSheet(
                        icon = R.drawable.icn_edit_user,
                        modifier = Modifier.padding(bottom = 24.dp),
                        backgroundIconColor = Color(0xFF73B2D6),
                        text = stringResource(id = R.string.edit_profile)
                    ) {
                        navController.navigate(Screens.UpdateProfileScreen.route)
                    }

                }
            },
            sheetState = modalSheetState
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState)
                    .background(color = bg)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    item {
                        Box {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(230.dp),
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
                                    .padding(horizontal = 24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 64.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row {
                                        Spacer(modifier = Modifier.weight(1f))
                                        ButtonAnimationIcon(
                                            modifier = Modifier.size(42.dp),
                                            icon = R.drawable.three_dot,
                                            iconPadding = 4.dp
                                        ) {
                                            coroutine.launch {
                                                if (modalSheetState.isVisible) {
                                                    modalSheetState.hide()
                                                } else {
                                                    modalSheetState.show()
                                                }
                                            }
                                        }
                                    }
                                }

                                Card(
                                    modifier = Modifier
                                        .padding(top = 75.dp)
                                        .size(120.dp),
                                    shape = CircleShape,
                                    border = BorderStroke(8.dp, bg),
                                ) {
                                    if (user.photoUri != null) {
                                        Image(
                                            painter = rememberAsyncImagePainter(model = user.photoUri),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .padding(8.dp)
                                                .fillMaxSize()
                                                .clip(
                                                    CircleShape
                                                ),
                                            contentScale = ContentScale.FillBounds
                                        )
                                    }
                                }

                                Text(
                                    text = user.username,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontSize = 26.sp,
                                    modifier = Modifier.padding(top = 18.dp, bottom = 10.dp)
                                )
                                Text(
                                    text = user.email,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                user.bio?.let {
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier.padding(top = 18.dp)
                                    )
                                }

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 42.dp)
                                ) {
                                    //other info
                                    Text(
                                        text = stringResource(id = R.string.other_inf),
                                        style = MaterialTheme.typography.titleMedium,
                                        modifier = Modifier.padding(bottom = 24.dp)
                                    )
                                    //account
                                    ProfileFiture(
                                        icon = R.drawable.lock,
                                        modifier = Modifier.padding(bottom = 24.dp),
                                        backgroundIconColor = Color(0xFFFF7E07),
                                        text = stringResource(id = R.string.account_scrty)
                                    ) {

                                    }
                                    //logout
                                    ProfileFiture(
                                        icon = R.drawable.logout,
                                        modifier = Modifier.padding(bottom = 150.dp),
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

                PullRefreshIndicator(
                    refreshing = profileViewModel.refreshing,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }


    }


}

@Composable
fun BottomSheet(
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
                tint = Color.Black
            )
        }

        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 16.dp)
        )
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