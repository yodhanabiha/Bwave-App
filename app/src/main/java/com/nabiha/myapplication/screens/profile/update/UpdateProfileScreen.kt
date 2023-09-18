package com.nabiha.myapplication.screens.profile.update

import android.net.Uri
import android.widget.Toast
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.nabiha.myapplication.R
import com.nabiha.myapplication.component.AnimationLoading
import com.nabiha.myapplication.component.ButtonAnimation
import com.nabiha.myapplication.component.ButtonAnimationIcon
import com.nabiha.myapplication.component.ErrorMessage
import com.nabiha.myapplication.component.TopStatusBar
import com.nabiha.myapplication.navigation.BottomBarScreen
import com.nabiha.myapplication.ui.theme.bg
import com.nabiha.myapplication.ui.theme.primary
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UpdateProfileScreen(
    navController: NavHostController,
    updateProfileViewModel: UpdateProfileViewModel = viewModel()
) {

    TopStatusBar(color = Color.Transparent, darkIcons = true)
    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current
    val user = updateProfileViewModel.user.value
    val isLoading = updateProfileViewModel.isLoading
    val isUpload = updateProfileViewModel.isUpload

    var isErr by remember {
        mutableStateOf(false)
    }

    var username by remember {
        mutableStateOf("")
    }
    var bio by remember {
        mutableStateOf("")
    }
    var bioChar by remember {
        mutableStateOf(bio.length)
    }

    bioChar = bio.length

    isErr = bioChar > 25

    val pullRefreshState = rememberPullRefreshState(
        refreshing = updateProfileViewModel.refreshing,
        onRefresh = {
            coroutine.launch {
                updateProfileViewModel.refreshData()
            }
        }
    )

    if (isLoading || isUpload) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 200.dp),
            contentAlignment = Alignment.Center
        ) {
            AnimationLoading(modifier = Modifier.wrapContentSize())
        }
    } else {
        var photoUri by remember { mutableStateOf(user.photoUri?.toUri()) }
        var bgUri by remember { mutableStateOf(user.photoUriBg?.toUri()) }
        val launcherPhoto =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
                photoUri = uri
            }

        val launcherBg =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
                bgUri = uri
            }
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
                        //bg
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(230.dp),
                            colors = CardDefaults.cardColors(Color(0xBF3A6DB8)),
                            shape = RectangleShape,
                        ) {
                            if (bgUri != null) {
                                Image(
                                    painter = rememberAsyncImagePainter(model = bgUri),
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
                            //top
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 50.dp),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                ButtonAnimationIcon(
                                    modifier = Modifier.size(40.dp),
                                    icon = R.drawable.arrow_left_2,
                                    iconPadding = 8.dp
                                ) {
                                    navController.navigate(BottomBarScreen.Profile.route)
                                }

                                Spacer(modifier = Modifier.weight(1f))
                                Image(
                                    painter = painterResource(id = R.drawable.icn_camera_transparent),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clickable {
                                            launcherBg.launch("image/*")
                                        }
                                )
                            }

                            //photo profile
                            Card(
                                modifier = Modifier
                                    .padding(top = 75.dp)
                                    .size(120.dp),
                                shape = CircleShape,
                                border = BorderStroke(8.dp, bg)
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (photoUri != null) {
                                        Image(
                                            painter = rememberAsyncImagePainter(model = photoUri),
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
                                    Image(
                                        painter = painterResource(id = R.drawable.icn_camera_transparent),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .padding(35.dp)
                                            .fillMaxSize()
                                            .clickable {
                                                launcherPhoto.launch("image/*")
                                            }
                                    )
                                }
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 36.dp),
                                horizontalAlignment = Alignment.Start
                            ) {
                                //username
                                TextFieldUpdateProfile(
                                    label = stringResource(id = R.string.username),
                                    firstValue = user.username,
                                    value = {
                                        username = it
                                    },
                                    modifier = Modifier.padding(top = 14.dp),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                //Bio
                                TextFieldUpdateProfile(
                                    label = stringResource(id = R.string.bio),
                                    firstValue = user.bio ?: "",
                                    value = {
                                        bio = it
                                    },
                                    modifier = Modifier.padding(top = 12.dp),
                                    shape = RoundedCornerShape(10.dp)
                                )

                                Row(
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp)
                                        .padding(top = 2.dp)
                                ) {
                                    if (isErr) {
                                        ErrorMessage(
                                            text = stringResource(id = R.string.bio_err),
                                            modifier = Modifier.wrapContentWidth()
                                        )
                                    }
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(
                                        text = "$bioChar/25",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = if (isErr) Color.Red else Color.Black
                                    )

                                }

                            }

                            ButtonAnimation(
                                buttonColor = primary,
                                modifier = Modifier
                                    .padding(horizontal = 20.dp, vertical = 6.dp),
                                style = MaterialTheme.typography.titleLarge,
                                text = stringResource(id = R.string.save)
                            ) {
                                if (!isErr && username.isNotBlank()) {
                                    coroutine.launch {
                                        updateProfileViewModel.saveData(
                                            username,
                                            bgUri,
                                            photoUri,
                                            context,
                                            navController,
                                            bio
                                        )
                                    }
                                } else {
                                    Toast.makeText(
                                        context,
                                        "please fill in the data correctly",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }


                            }


                        }
                    }
                }
            }
        }
    }


}