package com.nabiha.myapplication.screens.search.detail

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.nabiha.myapplication.R
import com.nabiha.myapplication.component.TopStatusBar
import com.nabiha.myapplication.navigation.BottomBarScreen
import com.nabiha.myapplication.ui.theme.bg
import com.nabiha.myapplication.ui.theme.greenText
import kotlinx.coroutines.launch

@Composable
fun DetailSearchScreen(
    navController: NavHostController,
    id: Int,
    detailSearchViewModel: DetailSearchViewModel = viewModel(
        factory = DetailSearchViewModelFactory(id)
    )
) {
    TopStatusBar()

    val umkm = detailSearchViewModel.umkm.value
    val coroutine = rememberCoroutineScope()

    if (!detailSearchViewModel.isLoading){

        var like by remember {
            mutableStateOf(umkm.like)
        }
        val painterlove = if (like) painterResource(id = R.drawable.red_love) else painterResource(id = R.drawable.icn_love)
        Log.d("WJAT", "DetailSearchScreen: ${umkm.like}")
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = bg
        ) {
            LazyColumn {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp)
                            .padding(horizontal = 20.dp)
                    ) {

                        //top
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Icon(
                                painter = painterResource(id = R.drawable.arrow_left_3),
                                contentDescription = "",
                                modifier = Modifier.clickable { navController.navigate(BottomBarScreen.Search.route) }
                            )
                            Text(
                                text = stringResource(id = R.string.details),
                                style = MaterialTheme.typography.titleLarge,
                                fontSize = 20.sp
                            )
                            Image(
                                painter = painterlove,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(30.dp)
                                    .clickable {
                                        coroutine.launch {
                                            if (like) {
                                                detailSearchViewModel.unlikeUmkm()
                                            } else {
                                                detailSearchViewModel.likeUmkm()
                                            }
                                            like = !like
                                        }
                                    }
                            )
                        }

                        Card(
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .fillMaxWidth()
                                .height(340.dp),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(model = umkm.image),
                                contentDescription = "",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.FillBounds
                            )
                        }


                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp)
                        ) {
                            // nama umkm
                            Text(
                                text = umkm.title,
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(top = 28.dp)
                            )

                            //rating views
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 14.dp),
                            ) {
                                Row(modifier = Modifier.padding(end = 6.dp)) {
                                    for (i in 0..4) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.star_rating_notfill),
                                            contentDescription = "",
                                            modifier = Modifier.padding(end = 4.dp)
                                        )
                                    }
                                }
                                //rata-rata rating
                                Text(
                                    text = umkm.rating.toString(),
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                Divider(
                                    modifier = Modifier
                                        .padding(horizontal = 12.dp)
                                        .width(1.dp)
                                        .height(18.dp),
                                    color = Color.Black
                                )
                                //views
                                Text(
                                    text = "${umkm.view} views",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            // clock
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(top = 14.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.clock),
                                    contentDescription = "",
                                    modifier = Modifier.size(20.dp),
                                    tint = Color.Black
                                )
                                Text(
                                    text = "07.00 - 21.00",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(horizontal = 12.dp)
                                )

                                Text(
                                    text = "open",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = greenText
                                )
                            }

                            //description
                            Text(
                                text = stringResource(id = R.string.description),
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(top = 32.dp)
                            )
                            Text(
                                text = umkm.description,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(top = 8.dp)
                            )

                            //location
                            Text(
                                text = stringResource(id = R.string.location),
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(top = 42.dp)
                            )

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 18.dp),
                                shape = RoundedCornerShape(10.dp),
                                elevation = CardDefaults.cardElevation(0.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.map),
                                    contentDescription = "",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.FillBounds
                                )
                            }

                            //reviews
                            Text(
                                text = stringResource(id = R.string.reviews),
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(top = 28.dp, bottom = 20.dp)
                            )

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(Color(0xFFEAE9E9)),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Row {
                                    Column(
                                        modifier = Modifier.padding(
                                            top = 20.dp,
                                            bottom = 18.dp,
                                            start = 20.dp,
                                            end = 24.dp
                                        ),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {

                                        Card(
                                            modifier = Modifier.size(50.dp),
                                            shape = CircleShape,
                                            colors = CardDefaults.cardColors(
                                                Color.Transparent
                                            )
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.icn_user),
                                                contentDescription = "",
                                                modifier = Modifier.fillMaxSize(),
                                                contentScale = ContentScale.FillBounds
                                            )
                                        }

                                        Text(
                                            text = "Name",
                                            style = MaterialTheme.typography.titleSmall,
                                            modifier = Modifier.padding(top = 8.dp)
                                        )
                                    }

                                    Column(modifier = Modifier.padding(top = 24.dp, bottom = 18.dp)) {

                                        Text(
                                            text = stringResource(id = R.string.rivew_ex),
                                            style = MaterialTheme.typography.bodyMedium
                                        )

                                        Text(
                                            text = "22 Des, 2022",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color(0xFF969696),
                                            modifier = Modifier.padding(top = 10.dp)
                                        )

                                    }

                                    Text(text = stringResource(id = R.string.all_reviews))

                                }
                            }


                        }


                    }

                }
            }

        }
    }

}

