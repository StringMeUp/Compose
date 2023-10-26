package com.sr.compose.ui.screens.bottomnavscreens.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.sr.compose.domain.usecase.AuthState
import com.sr.compose.ui.theme.ComposeMoviesTheme
import com.sr.compose.ui.widgets.AppButton
import com.sr.compose.ui.widgets.default
import com.sr.compose.util.launch

@Composable
fun ProfileScreen(request_token: String? = null) {
    ProfileContent()
}

@Composable
fun ProfileContent(
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val uiState = viewModel.authState.value
    InitialContent(
        uiState = uiState,
        triggerAuth = viewModel::getRequestToken,
        handleRedirect = { request, launchIntent ->
            viewModel.handleRedirect(
                request,
                launchIntent
            )
        }
    )
}

@Composable
@Preview
fun InitialContent(
    uiState: AuthState = AuthState(),
    triggerAuth: () -> Unit = {},
    handleRedirect: (request: WebResourceRequest?, launchIntent: (getUri: () -> Uri) -> Unit) -> Boolean = { _, _ -> false },
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (infoText, loginButton) = createRefs()

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
                    top.linkTo(infoText.bottom)
                    start.linkTo(infoText.start)
                    end.linkTo(infoText.end)
                },
            onCLick = {
                triggerAuth()
            })

        if (uiState.hasRt)
            LoadWebUrl(url = uiState.authUrl,
                handleRedirect = { request, launchIntent -> handleRedirect(request, launchIntent) })
    }
}


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun LoadWebUrl(
    url: String?,
    handleRedirect: (webResourceRequest: WebResourceRequest?, (getUri: () -> Uri) -> Unit) -> Boolean = { _, _ -> false },
) {
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
                        return handleRedirect(request) { uri ->
                            Intent(Intent.ACTION_VIEW, uri()).launch(context)
                        }
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
fun ProfilePreview() {
    ComposeMoviesTheme {
        ProfileContent()
    }
}