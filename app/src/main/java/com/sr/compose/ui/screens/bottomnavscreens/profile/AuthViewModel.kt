package com.sr.compose.ui.screens.bottomnavscreens.profile

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.WebResourceRequest
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sr.compose.api.NetworkConstants
import com.sr.compose.domain.usecase.AuthState
import com.sr.compose.domain.usecase.AuthUseCase
import com.sr.compose.util.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val useCase: AuthUseCase) : ViewModel() {
    var authState = mutableStateOf(AuthState())
        private set

    fun getRequestToken() {
        viewModelScope.launch {
            authState.value = useCase.getRequestToken()
        }
    }

    fun getAppRedirectUri(request: WebResourceRequest?): Uri {
        val query = request?.url?.getQueryParameters(NetworkConstants.PATH_REDIRECT)
        val rt = request?.url?.pathSegments?.find {
            it != NetworkConstants.PATH_AUTH && it != NetworkConstants.PATH_CONFIRMED
        }
        return Uri.parse("${query?.first()}/${rt}")
    }


     fun handleRedirect(request: WebResourceRequest?, launchIntent: (getUri: () -> Uri) -> Unit = {}): Boolean {
        val isAppRedirect = request?.url?.lastPathSegment == NetworkConstants.PATH_CONFIRMED
        if (isAppRedirect) {
            launchIntent { getAppRedirectUri(request) }
//            Intent(Intent.ACTION_VIEW, getAppRedirectUri(request = request)).launch(context)
            return true
        }
        return false
    }
}

