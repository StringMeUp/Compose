package com.sr.compose.ui.screens.bottomnavscreens.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sr.compose.domain.usecase.AuthState
import com.sr.compose.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val useCase: AuthUseCase) : ViewModel() {

    var authState = mutableStateOf(AuthState())
        private set

    fun getRequestToken() {
        viewModelScope.launch {
            authState.value = useCase.getRequestToken()
        }
    }
}