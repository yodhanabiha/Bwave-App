package com.nabiha.myapplication.material

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.nabiha.myapplication.R

@Composable
fun AnimationLoading(modifier: Modifier){
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes
        (R.raw.animation_loading)
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        LottieAnimation(
            modifier = modifier,
            composition = composition,
            iterations = LottieConstants.IterateForever,
        )
        Text(text = "LOADING...", style = MaterialTheme.typography.titleLarge)
    }
}

