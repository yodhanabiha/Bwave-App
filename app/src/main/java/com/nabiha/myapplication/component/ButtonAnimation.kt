package com.nabiha.myapplication.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nabiha.myapplication.ui.theme.grayTransparent
import kotlinx.coroutines.launch

@Composable
fun ButtonAnimation(
    buttonColor: Color,
    modifier: Modifier,
    style: TextStyle,
    fontColor : Color = Color.White,
    text: String,
    shape: Shape = RoundedCornerShape(10.dp),
    onClick: () -> Unit
) {

    val interactionSource = MutableInteractionSource()

    val coroutineScope = rememberCoroutineScope()

    val scale = remember {
        Animatable(1f)
    }

    Box(
        modifier = Modifier
            .scale(scale = scale.value)
            .background(
                color = buttonColor,
                shape = shape
            )
            .clickable(interactionSource = interactionSource, indication = null) {
                coroutineScope.launch {
                    scale.animateTo(
                        0.9f,
                        animationSpec = tween(100),
                    )
                    scale.animateTo(
                        1f,
                        animationSpec = tween(100),
                    )
                    onClick.invoke()
                }
            }
    ) {
        Text(
            text = text,
            modifier = modifier,
            style = style,
            color = fontColor,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ButtonAnimationIcon(
    buttonColor: Color = grayTransparent,
    modifier: Modifier,
    icon : Int,
    iconColor : Color = Color.White,
    iconPadding : Dp = 0.dp,
    shape: Shape = CircleShape,
    onClick: () -> Unit
) {

    val interactionSource = MutableInteractionSource()

    val coroutineScope = rememberCoroutineScope()

    val scale = remember {
        Animatable(1f)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .scale(scale = scale.value)
            .background(
                color = buttonColor,
                shape = shape
            )
            .clickable(interactionSource = interactionSource, indication = null) {
                coroutineScope.launch {
                    scale.animateTo(
                        0.9f,
                        animationSpec = tween(100),
                    )
                    scale.animateTo(
                        1f,
                        animationSpec = tween(100),
                    )
                    onClick.invoke()
                }
            }
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = "",
            tint = iconColor,
            modifier = Modifier.padding(iconPadding).fillMaxSize()
        )
    }
}

