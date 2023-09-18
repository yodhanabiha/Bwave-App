package com.nabiha.myapplication.screens.search.index

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.nabiha.myapplication.R
import com.nabiha.myapplication.component.ButtonAnimation
import com.nabiha.myapplication.component.TopStatusBar
import com.nabiha.myapplication.component.gridItems
import com.nabiha.myapplication.ui.theme.bgCardSearch
import com.nabiha.myapplication.ui.theme.primary
import com.nabiha.myapplication.ui.theme.textGray
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalLayoutApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun SearchScreen(
    navController: NavHostController,
    searchViewModel: SearchViewModel = viewModel()
) {

    TopStatusBar()
    var search by remember {
        mutableStateOf("")
    }
    val dataUMKM = searchViewModel.umkm

    val coroutine = rememberCoroutineScope()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        contentPadding = PaddingValues(horizontal = 24.dp),
        content = {
            item {
                //search and sort
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Card(
                        shape = RectangleShape,
                        backgroundColor = bgCardSearch,
                        elevation = 0.dp,
                        modifier = Modifier.padding(end = 14.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icn_sort),
                            contentDescription = "",
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                        )
                    }

                    BasicTextField(
                        value = search,
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = { search = it },
                        textStyle = MaterialTheme.typography.bodyMedium,
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .background(bgCardSearch, RectangleShape)
                                    .padding(vertical = 14.dp, horizontal = 20.dp)
                            ) {
                                Box {
                                    if (search.isEmpty()) {
                                        Text(
                                            text = stringResource(id = R.string.search),
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = textGray
                                        )
                                    }
                                    innerTextField()
                                }

                                Spacer(modifier = Modifier.weight(1f))
                                Icon(
                                    painter = painterResource(id = R.drawable.icn_search_2),
                                    contentDescription = "",
                                    Modifier.size(26.dp)
                                )

                            }
                        }
                    )
                }
            }
            item {
                //location & rating & time
                Row(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth()
                        .height(36.dp)
                ) {

                    Card(
                        shape = RectangleShape,
                        backgroundColor = bgCardSearch,
                        elevation = 0.dp,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(0.2f)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.location),
                                contentDescription = "",
                                alignment = Alignment.CenterStart
                            )
                            Text(
                                text = stringResource(id = R.string.location),
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 4.dp, end = 8.dp)
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.arrow_down_small),
                                contentDescription = ""
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(0.01f))
                    Card(
                        shape = RectangleShape,
                        backgroundColor = bgCardSearch,
                        elevation = 0.dp,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(0.2f)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.star),
                                contentDescription = "",
                                alignment = Alignment.CenterStart
                            )
                            Text(
                                text = stringResource(id = R.string.rating),
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 12.dp, end = 18.dp)
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.arrow_down_small),
                                contentDescription = ""
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(0.01f))
                    Card(
                        shape = RectangleShape,
                        backgroundColor = bgCardSearch,
                        elevation = 0.dp,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(0.2f)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.clock),
                                contentDescription = "",
                                alignment = Alignment.CenterStart
                            )
                            Text(
                                text = stringResource(id = R.string.time),
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 12.dp, end = 16.dp)
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.arrow_down_small),
                                contentDescription = ""
                            )
                        }
                    }
                }
            }
            item {
                //popular search
                val label = listOf(
                    "Desain",
                    "Toko Baju",
                    "Makanan",
                    "Penginapan",
                    "Cafe"
                )

                Text(
                    text = stringResource(id = R.string.pop_search),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 34.dp, bottom = 17.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                ) {

                    FlowRow {
                        label.forEach { iLabel ->
                            Card(
                                onClick = { /*TODO*/ },
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier.padding(end = 8.dp),
                                elevation = 0.dp
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(
                                            Brush.verticalGradient(
                                                colors = listOf(
                                                    Color(0xFF73B2D6),
                                                    Color(0x4D93A2D8)
                                                )
                                            )
                                        ),
                                ) {
                                    Text(
                                        text = iLabel,
                                        style = MaterialTheme.typography.titleSmall,
                                        modifier = Modifier
                                            .padding(
                                                vertical = 10.dp,
                                                horizontal = 24.dp
                                            )

                                    )
                                }

                            }
//
                        }
                    }

                }
            }
            item {
                Text(
                    text = stringResource(id = R.string.recommended),
                    modifier = Modifier.padding(top = 36.dp, bottom = 24.dp),
                    style = MaterialTheme.typography.titleMedium,
                    softWrap = false,
                    overflow = TextOverflow.Ellipsis
                )
            }

            var index = 0

            gridItems(
                data = dataUMKM,
                nColumns = 2,
                verticalSpace = 16.dp,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) { umkm ->
                Card(
                    elevation = 4.dp,
                    backgroundColor = Color.White,
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        Card(
                            elevation = 0.dp,
                            backgroundColor = Color.White,
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier
                                .height(135.dp)
                                .fillMaxWidth()
                        ) {
                            Box {
                                Image(
                                    painter = rememberAsyncImagePainter(model = umkm.image),
                                    contentDescription = "",
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier.fillMaxSize()
                                )

                                Row(
                                    modifier = Modifier
                                        .padding(top = 6.dp, end = 6.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Card(
                                        shape = CircleShape,
                                        backgroundColor = Color.White,
                                        elevation = 0.dp
                                    ) {

                                        var like by remember {
                                            mutableStateOf(umkm.like)
                                        }

                                        val painter =
                                            if (like) painterResource(id = R.drawable.red_love) else painterResource(
                                                id = R.drawable.icn_love
                                            )
                                        Image(
                                            painter = painter,
                                            contentDescription = "",
                                            modifier = Modifier
                                                .padding(6.dp)
                                                .size(18.dp)
                                                .clickable {
                                                    coroutine.launch {
                                                        if (like) {
                                                            searchViewModel.unlikeUmkm(umkm.id, index)
                                                        } else {
                                                            searchViewModel.likeUmkm(umkm.id, index)
                                                        }
                                                        like = !like
                                                    }
                                                }
                                        )
                                    }
                                }

                            }
                        }


                        Text(
                            text = umkm.title,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .padding(top = 12.dp, start = 4.dp)
                                .fillMaxWidth()
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 6.dp, bottom = 16.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.star_2),
                                contentDescription = "",
                                modifier = Modifier
                                    .padding(
                                        start = 4.dp,
                                        end = 8.dp
                                    )
                                    .size(20.dp)
                            )

                            Text(
                                text = "${umkm.star} (${umkm.review})",
                                color = textGray,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            ButtonAnimation(
                                buttonColor = primary,
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .fillMaxWidth(),
                                style = MaterialTheme.typography.titleSmall,
                                text = stringResource(id = R.string.details),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                navController.navigate("detail_search_screen/${umkm.id}")

                            }
                        }


                    }
                }
                index++
            }
        })


}

