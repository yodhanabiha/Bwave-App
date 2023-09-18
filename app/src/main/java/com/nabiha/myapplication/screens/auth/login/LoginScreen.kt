package com.nabiha.myapplication.screens.auth.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.nabiha.myapplication.R
import com.nabiha.myapplication.data.model.user.LoginUser
import com.nabiha.myapplication.component.ButtonAnimation
import com.nabiha.myapplication.component.ErrorMessage
import com.nabiha.myapplication.component.OutlinedButtonAnimation
import com.nabiha.myapplication.component.TopStatusBar
import com.nabiha.myapplication.navigation.Screens
import com.nabiha.myapplication.ui.theme.bg
import com.nabiha.myapplication.ui.theme.primary
import com.nabiha.myapplication.ui.theme.textGray
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = viewModel()
) {

    TopStatusBar(color = Color.Transparent, darkIcons = false)

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }
    var showPassword by remember {
        mutableStateOf(false)
    }
    var emailErr by remember {
        mutableStateOf(false)
    }

    var passwordErr by remember {
        mutableStateOf(false)
    }

    val paddingBottomEmail = if (emailErr) 1.dp else 20.dp
    val paddingBottomPassword = if (passwordErr) 1.dp else 12.dp

    emailErr = email.isNotBlank() && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    passwordErr = password.length < 6 && password.isNotBlank()

    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()

    Surface(
        modifier = Modifier
            .fillMaxSize()
        ,
        color = bg
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
        ) {
            item {
                Column {
                    Card(
                        modifier = Modifier
                            .padding(bottom = 26.dp)
                            .fillMaxWidth()
                            .height(258.dp),
                        shape = RectangleShape
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.bg_login),
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillBounds
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp)
                    ) {

                        Text(
                            text = stringResource(id = R.string.lets_connect),
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(bottom = 26.dp)
                        )

                        //Email
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            textStyle = MaterialTheme.typography.bodyLarge,
                            placeholder = { Text(text = stringResource(id = R.string.email_ex)) },
                            singleLine = true,
                            isError = emailErr,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .padding(bottom = paddingBottomEmail)
                                .fillMaxWidth(),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                textColor = Color.Black,
                                placeholderColor = textGray,
                                unfocusedBorderColor = Color(0x80000000),
                                focusedBorderColor = primary,
                                cursorColor = Color.Black,
                                errorBorderColor = Color.Red,
                            )
                        )
                        if (emailErr) {
                            ErrorMessage(
                                text = stringResource(id = R.string.email_err),
                                modifier = Modifier.padding(bottom = 20.dp).fillMaxWidth()
                            )
                        }

                        //Password
                        val iconPw =
                            if (showPassword) painterResource(id = R.drawable.eye_open) else painterResource(
                                id = R.drawable.eye_close
                            )

                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            textStyle = MaterialTheme.typography.bodyLarge,
                            placeholder = { Text(text = stringResource(id = R.string.password)) },
                            singleLine = true,
                            isError = passwordErr,
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
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                textColor = Color.Black,
                                placeholderColor = textGray,
                                unfocusedBorderColor = Color(0x80000000),
                                focusedBorderColor = primary,
                                cursorColor = Color.Black,
                                errorBorderColor = Color.Red,
                                trailingIconColor = Color.Gray
                            )
                        )
                        if (passwordErr) {
                            ErrorMessage(
                                text = stringResource(id = R.string.password_err),
                                modifier = Modifier.padding(bottom = 12.dp).fillMaxWidth()
                            )
                        }
                        //forgot password
                        Text(
                            text = stringResource(id = R.string.fg_passwrd),
                            style = MaterialTheme.typography.bodyMedium,
                            color = textGray,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 20.dp),
                            textAlign = TextAlign.End
                        )

                        //button login
                        ButtonAnimation(
                            buttonColor = primary,
                            modifier = Modifier
                                .padding(vertical = 20.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.titleMedium,
                            fontColor = Color.White,
                            text = stringResource(id = R.string.signin),
                            onClick = {
                                coroutine.launch {
                                    if (email.isNotBlank() && password.isNotBlank() && !emailErr && !passwordErr){
                                        loginViewModel.login(
                                            loginUser = LoginUser(
                                                email = email,
                                                password = password,
                                            ),
                                            context = context,
                                            navController = navController
                                        )
                                    }else{
                                        Toast.makeText(context,"Invalid Input!", Toast.LENGTH_LONG).show()
                                    }
                                }
                            },
                            shape = RoundedCornerShape(30.dp)
                        )

                        //or
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp, top = 20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Divider(
                                modifier = Modifier.weight(1f),
                                thickness = 1.dp,
                                color = Color.Gray
                            )
                            Text(
                                text = "or",
                                style = MaterialTheme.typography.titleSmall,
                                color = Color.Gray,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            Divider(
                                modifier = Modifier.weight(1f),
                                thickness = 1.dp,
                                color = Color.Gray
                            )
                        }

                        //button facebook
                        OutlinedButtonAnimation(
                            borderColor = Color.Black,
                            modifier = Modifier
                                .padding(vertical = 14.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.titleMedium,
                            text = stringResource(id = R.string.signin_fb),
                            icon = painterResource(id = R.drawable.icn_facebook),
                            iconSize = 30.dp,
                            shape = RoundedCornerShape(30.dp)
                        ) {

                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        //button google
                        OutlinedButtonAnimation(
                            borderColor = Color.Black,
                            modifier = Modifier
                                .padding(vertical = 14.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.titleMedium,
                            text = stringResource(id = R.string.signin_google),
                            icon = painterResource(id = R.drawable.icn_google),
                            iconSize = 30.dp,
                            shape = RoundedCornerShape(30.dp)
                        ) {

                        }

                        //sign up
                        Row(modifier = Modifier.padding(top = 20.dp, bottom = 100.dp)) {
                            Text(
                                text = stringResource(id = R.string.donthaveaccount),
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Gray
                            )
                            ClickableText(
                                text = AnnotatedString(stringResource(id = R.string.signup)),
                                modifier = Modifier.padding(start = 8.dp),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = Color(0xFF5031D2)
                                ),
                                onClick = {
                                    navController.navigate(Screens.RegisterScreen.route)
                                }
                            )
                        }
                        Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.systemBars))
                    }
                }
            }
        }
    }
}
