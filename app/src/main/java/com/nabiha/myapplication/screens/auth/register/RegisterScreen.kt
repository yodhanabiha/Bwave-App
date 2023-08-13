package com.nabiha.myapplication.screens.auth.register

import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.nabiha.myapplication.R
import com.nabiha.myapplication.data.model.user.RegisterUser
import com.nabiha.myapplication.material.ButtonAnimation
import com.nabiha.myapplication.material.ErrorMessage
import com.nabiha.myapplication.material.TopStatusBar
import com.nabiha.myapplication.navigation.Screens
import com.nabiha.myapplication.ui.theme.bg
import com.nabiha.myapplication.ui.theme.lightGray
import com.nabiha.myapplication.ui.theme.primary
import com.nabiha.myapplication.ui.theme.textGray
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    navController: NavHostController,
    registerViewModel: RegisterViewModel = viewModel()
) {
    TopStatusBar()
    val textFIeldColor = TextFieldDefaults.outlinedTextFieldColors(
        textColor = Color.Black,
        placeholderColor = textGray,
        unfocusedBorderColor = Color(0x80000000),
        focusedBorderColor = primary,
        cursorColor = Color.Black,
        errorBorderColor = Color.Red,
    )

    var email by remember {
        mutableStateOf("")
    }

    var username by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var cPassword by remember {
        mutableStateOf("")
    }

    var number by remember {
        mutableStateOf("")
    }

    var emailErr by remember {
        mutableStateOf(false)
    }
    var passwordErr by remember {
        mutableStateOf(false)
    }
    var cPasswordErr by remember {
        mutableStateOf(false)
    }
    var numberErr by remember {
        mutableStateOf(false)
    }

    emailErr = email.isNotBlank() && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    passwordErr = password.length < 6 && password.isNotBlank()
    cPasswordErr = cPassword.isNotBlank() && cPassword != password
    numberErr = number.length < 8 && number.isNotBlank()

    val paddingBottomEmail = if (emailErr) 1.dp else 12.dp
    val paddingBottomnumber = if (numberErr) 1.dp else 12.dp
    val paddingBottomPassword = if (passwordErr) 1.dp else 12.dp

    var showPassword by remember {
        mutableStateOf(false)
    }
    var showCpassword by remember {
        mutableStateOf(false)
    }

    val iconPw =
        if (showPassword) painterResource(id = R.drawable.eye_open) else painterResource(
            id = R.drawable.eye_close
        )

    val iconCpw =
        if (showCpassword) painterResource(id = R.drawable.eye_open) else painterResource(
            id = R.drawable.eye_close
        )

    val interactionSource = MutableInteractionSource()

    val coroutineScope = rememberCoroutineScope()

    val scale = remember {
        Animatable(1f)
    }

    val context = LocalContext.current

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        color = bg
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
                    .imePadding()
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {
                        //back
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Card(
                                modifier = Modifier
                                    .scale(scale = scale.value)
                                    .padding(vertical = 34.dp)
                                    .size(48.dp)
                                    .clickable(
                                        interactionSource = interactionSource,
                                        indication = null
                                    ) {
                                        coroutineScope.launch {
                                            scale.animateTo(
                                                0.9f,
                                                animationSpec = tween(100),
                                            )
                                            scale.animateTo(
                                                1f,
                                                animationSpec = tween(100),
                                            )
                                            navController.popBackStack()
                                        }
                                    },
                                shape = CircleShape,
                                colors = CardDefaults.cardColors(
                                    lightGray
                                )
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.arrow_left_),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .padding(6.dp)
                                        .fillMaxSize()
                                )
                            }

                            Text(
                                text = stringResource(id = R.string.set_acc),
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(start = 42.dp)
                            )
                        }

                        Text(
                            text = stringResource(id = R.string.please_complete_reg),
                            style = MaterialTheme.typography.titleMedium,
                            color = textGray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        //username
                        OutlinedTextField(
                            value = username,
                            onValueChange = { username = it },
                            textStyle = MaterialTheme.typography.bodyLarge,
                            placeholder = { androidx.compose.material.Text(text = stringResource(id = R.string.username)) },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .padding(bottom = 12.dp, top = 28.dp)
                                .fillMaxWidth(),
                            colors = textFIeldColor
                        )
                        //email
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            textStyle = MaterialTheme.typography.bodyLarge,
                            placeholder = { androidx.compose.material.Text(text = stringResource(id = R.string.email)) },
                            singleLine = true,
                            isError = emailErr,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .padding(bottom = paddingBottomEmail)
                                .fillMaxWidth(),
                            colors = textFIeldColor
                        )
                        if (emailErr) {
                            ErrorMessage(
                                text = stringResource(id = R.string.email_err),
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                        }
                        //number
                        OutlinedTextField(
                            value = number,
                            onValueChange = {
                                if (it.isDigitsOnly()) number = it

                            },
                            textStyle = MaterialTheme.typography.bodyLarge,
                            placeholder = { androidx.compose.material.Text(text = stringResource(id = R.string.phn_num)) },
                            singleLine = true,
                            isError = numberErr,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            leadingIcon = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(start = 18.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.icn__indonesia),
                                        contentDescription = "",
                                    )
                                    Text(
                                        text = "+62",
                                        style = MaterialTheme.typography.titleMedium,
                                        modifier = Modifier.padding(horizontal = 10.dp)
                                    )
                                    Divider(
                                        modifier = Modifier
                                            .padding(end = 16.dp)
                                            .height(25.dp)
                                            .width(1.2.dp),
                                        color = Color.Black
                                    )
                                }

                            },
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .padding(bottom = paddingBottomnumber)
                                .fillMaxWidth(),
                            colors = textFIeldColor
                        )
                        if (numberErr) {
                            ErrorMessage(
                                text = stringResource(id = R.string.number_err),
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                        }
                        //password
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            textStyle = MaterialTheme.typography.bodyLarge,
                            placeholder = { androidx.compose.material.Text(text = stringResource(id = R.string.password)) },
                            singleLine = true,
                            isError = passwordErr,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            shape = RoundedCornerShape(20.dp),
                            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                Icon(
                                    painter = iconPw,
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clickable {
                                            showPassword = !showPassword
                                        })
                            },
                            modifier = Modifier
                                .padding(bottom = paddingBottomPassword)
                                .fillMaxWidth(),
                            colors = textFIeldColor
                        )
                        if (passwordErr) {
                            ErrorMessage(
                                text = stringResource(id = R.string.password_err),
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                        }
                        //confirmpassword
                        OutlinedTextField(
                            value = cPassword,
                            onValueChange = { cPassword = it },
                            textStyle = MaterialTheme.typography.bodyLarge,
                            placeholder = { androidx.compose.material.Text(text = stringResource(id = R.string.cPassword)) },
                            singleLine = true,
                            isError = cPasswordErr,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            shape = RoundedCornerShape(20.dp),
                            visualTransformation = if (showCpassword) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                Icon(
                                    painter = iconCpw,
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clickable {
                                            showCpassword = !showCpassword
                                        })
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = textFIeldColor
                        )
                        if (cPasswordErr) {
                            ErrorMessage(
                                text = stringResource(id = R.string.cPassword_err),
                                modifier = Modifier.padding(bottom = 12.dp)
                            )

                        }

                    }

                }
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .padding(bottom = 32.dp)
                    ) {
                        ButtonAnimation(
                            buttonColor = primary,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp),
                            style = MaterialTheme.typography.titleMedium,
                            text = stringResource(id = R.string.creat_acc),
                            shape = RoundedCornerShape(30.dp)
                        ) {
                            coroutineScope.launch {
                                if (
                                    password.isNotBlank() && email.isNotBlank() &&
                                    cPassword.isNotBlank() && number.isNotBlank() &&
                                    username.isNotBlank() && !emailErr &&
                                    !passwordErr && !cPasswordErr && !numberErr
                                ) {
                                    registerViewModel.addData(
                                        RegisterUser(
                                            username,
                                            "+62$number",
                                            email,
                                            password,
                                            cPassword,
                                        ),
                                        context,
                                        navController
                                    )
                                }
                                else{
                                    Toast.makeText(context,"Harap isi semua data!", Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(32.dp))
                    }

                }

            }
        }
    }

}

