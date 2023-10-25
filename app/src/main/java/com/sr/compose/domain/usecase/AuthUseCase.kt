package com.sr.compose.domain.usecase

import com.sr.compose.api.NetworkConstants
import com.sr.compose.domain.repository.AuthRepository
import com.sr.compose.util.evaluateResource
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authRepo: AuthRepository,
) : BaseUseCase<Unit, AuthState>() {
    var authState = AuthState()
    override suspend fun getRequestToken(): AuthState {
        authRepo.getRequestToken().collect { resource ->
            resource.evaluateResource(
                onLoading = {},
                onSuccessAuth = { _, authUrl ->
                    authUrl?.let {
                        authState = authState.copy(
                            authUrl = "$it${NetworkConstants.REDIRECT_URL}",
                            hasRt = authUrl.isNotBlank()
                        )
                    }
                }, onError = {})
        }
        return authState
    }
}

data class AuthState(
    val authUrl: String? = null,
    val hasRt: Boolean = false,
)