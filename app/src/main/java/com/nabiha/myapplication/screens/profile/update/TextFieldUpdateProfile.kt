package com.nabiha.myapplication.screens.profile.update

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.nabiha.myapplication.ui.theme.primary

@Composable
fun TextFieldUpdateProfile(
    label: String,
    firstValue:String,
    value: (String) -> Unit,
    modifier: Modifier,
    shape: Shape = RoundedCornerShape(20.dp)
) {
    val textFieldColor = TextFieldDefaults.outlinedTextFieldColors(
        textColor = Color.Black,
        unfocusedBorderColor = Color(0x80000000),
        focusedBorderColor = primary,
        cursorColor = Color.Black,
        errorBorderColor = Color.Red,
    )

    var values by remember {
        mutableStateOf(firstValue)
    }


    Text(
        text = label,
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier
    )
    OutlinedTextField(
        value = values,
        onValueChange = { values = it },
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        shape = shape,
        modifier = Modifier
            .padding(top = 12.dp)
            .fillMaxWidth(),
        colors = textFieldColor
    )

    value(values)
}