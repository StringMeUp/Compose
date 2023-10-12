package com.sr.compose.ui.screens.bottomnavscreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.sr.compose.ui.widgets.AppButton
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.sr.compose.R
import com.sr.compose.ui.helper.default
import com.sr.compose.ui.theme.ComposeMoviesTheme
import com.sr.compose.ui.widgets.AppTextField
import kotlinx.coroutines.launch
import kotlin.math.log

@Composable
fun ProfileScreen() {
    LogIn()
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LogIn() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (infoText, loginTextField, passwordTextField, loginButton, registerButton) = createRefs()
        val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
        var emailText by remember { mutableStateOf("") }
        var passWordText by remember { mutableStateOf("") }
        val scope = rememberCoroutineScope()

        Text(buildAnnotatedString {
            withStyle(default) {
                append("Hello, if you haven't logged in yet, please do so.\n\n")
                append("If you haven't got an account with us, register firstly. ")
            }
        }, modifier = Modifier
            .padding(24.dp)
            .constrainAs(infoText) {
                centerHorizontallyTo(parent)
            })

        AppTextField(label = "E-mail",
            emailText,
            onValueChange = { emailText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp)
                .constrainAs(loginTextField) {
                    top.linkTo(infoText.bottom)
                })

        AppTextField(label = "Password",
            passWordText,
            onValueChange = { passWordText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 9.dp)
                .constrainAs(passwordTextField) {
                    top.linkTo(loginTextField.bottom)
                })

        AppButton(text = "Log in",
            modifier = Modifier
                .padding(top = 24.dp)
                .defaultMinSize(minWidth = 140.dp, minHeight = 48.dp)
                .constrainAs(loginButton) {
                    top.linkTo(passwordTextField.bottom)
                    start.linkTo(passwordTextField.start)
                    end.linkTo(passwordTextField.end)
                })

        AppButton(text = "Register",
            modifier = Modifier
                .padding(top = 12.dp)
                .defaultMinSize(minWidth = 140.dp, minHeight = 48.dp)
                .constrainAs(registerButton) {
                    top.linkTo(loginButton.bottom)
                    centerHorizontallyTo(parent)
                },
            onClickEvent = {
                scope.launch {
                    sheetState.show()
                }
            })

        if (sheetState.isVisible) {
            ModalBottomSheet(
                onDismissRequest = {
                    scope.launch { sheetState.hide() }

                }, sheetState = SheetState(true)
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.w_bg)
                ) {
                    Column() {
                        Button(modifier = Modifier.wrapContentSize(), onClick = {
                            scope.launch { sheetState.hide() }
                        }) {
                            Text("Hide bottom sheet")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestUI() {
    ComposeMoviesTheme {
        LogIn()
    }
}