package com.nabiha.myapplication.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


@Composable
fun OutlinedButtonAnimation(
    borderColor: Color,
    modifier: Modifier,
    style: TextStyle,
    fontColor : Color = Color.Black,
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
                color = Color.Transparent,
                shape = shape,
            )
            .border(width = 1.dp, color = borderColor, shape = shape)
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
fun OutlinedButtonAnimation(
    borderColor: Color,
    modifier: Modifier,
    style: TextStyle,
    fontColor : Color = Color.Black,
    text: String,
    shape: Shape = RoundedCornerShape(10.dp),
    icon: Painter,
    iconSize : Dp,
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
                color = Color.Transparent,
                shape = shape,
            )
            .border(width = 1.dp, color = borderColor, shape = shape)
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

        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Image(painter = icon, contentDescription = "", modifier = Modifier.size(iconSize))
            Text(
                text = text,
                modifier = Modifier.padding(start = 14.dp),
                style = style,
                color = fontColor,
                textAlign = TextAlign.Center
            )
        }

    }

}