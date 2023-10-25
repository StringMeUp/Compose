package com.sr.compose.ui.screens.bottomnavscreens.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.sr.compose.R
import com.sr.compose.api.NetworkConstants
import com.sr.compose.ui.theme.ComposeMoviesTheme
import com.sr.compose.ui.widgets.AppButton
import com.sr.compose.ui.widgets.AppTextField
import com.sr.compose.ui.widgets.default
import com.sr.compose.util.launch
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun ProfileScreen(request_token: String? = null) {
    Timber.tag("REQUEST_TOKEN_VALID").d("$request_token")
    LogIn()
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LogIn(viewModel: ProfileViewModel = hiltViewModel()) {
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

        AppButton(text = "Log in",
            modifier = Modifier
                .padding(top = 24.dp)
                .defaultMinSize(minWidth = 140.dp, minHeight = 42.dp)
                .constrainAs(loginButton) {
                    top.linkTo(passwordTextField.bottom)
                    start.linkTo(passwordTextField.start)
                    end.linkTo(passwordTextField.end)
                },
            onCLick = {
                viewModel.getRequestToken()
            })


        AppButton(text = "Continue as guest",
            modifier = Modifier
                .padding(top = 4.dp)
                .defaultMinSize(minWidth = 140.dp, minHeight = 42.dp)
                .constrainAs(registerButton) {
                    top.linkTo(loginButton.bottom)
                    centerHorizontallyTo(parent)
                },
            onCLick = {
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
                    modifier = Modifier.fillMaxSize(), color = colorResource(id = R.color.w_bg)
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

        if (viewModel.authState.value.hasRt)
            LoadWebUrl(url = viewModel.authState.value.authUrl, viewModel = viewModel)
//            urlIntent(url = viewModel.authState.value.authUrl)
    }
}

@Composable
fun urlIntent(url: String?) {

    val webIntent: Intent = Uri.parse(url).let { webpage ->
        Intent(Intent.ACTION_VIEW, webpage)
    }
    startActivity(LocalContext.current, webIntent, null)
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun LoadWebUrl(url: String?, viewModel: ProfileViewModel) {
    AndroidView(
        factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?,
                    ): Boolean {
                        val isAppRedirect =
                            request?.url?.lastPathSegment == NetworkConstants.PATH_CONFIRMED
                        if (isAppRedirect) {
                            Intent(
                                Intent.ACTION_VIEW,
                                viewModel.getAppRedirectUri(request = request)
                            ).launch(context)
                            return true
                        }
                        return false
                    }
                }
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                url?.let { loadUrl(it) }
            }
        })
}

@Preview(showBackground = true)
@Composable
fun TestUI() {
    ComposeMoviesTheme {
        LogIn()
    }
}