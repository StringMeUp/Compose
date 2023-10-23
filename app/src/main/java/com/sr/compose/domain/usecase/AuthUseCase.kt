package com.sr.compose.domain.usecase

import android.content.SharedPreferences
import com.sr.compose.domain.repository.AuthRepository
import com.sr.compose.util.evaluateResource
import timber.log.Timber
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authRepo: AuthRepository,
) : BaseUseCase<Unit, AuthState>() {
    var authState = AuthState()

    override suspend fun getRequestToken(): AuthState {
        authRepo.getRequestToken().collect {
            it.evaluateResource(
                onLoading = {},
                onSuccess = { rt ->
                    rt?.let { authState = authState.copy(requestToken = it.requestToken) }
                }, onError = {})
        }

        Timber.tag("REQUEST_TOKEN").d("${authState.requestToken}")
        return authState
    }
}

data class AuthState(
    val requestToken: String? = null,
)