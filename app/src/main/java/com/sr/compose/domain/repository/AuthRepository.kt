package com.sr.compose.domain.repository

import com.sr.compose.api.MovieApi
import com.sr.compose.domain.RemoteSource
import javax.inject.Inject

class AuthRepository @Inject constructor(val api: MovieApi) {

    fun getRequestToken() = RemoteSource.launchResultFlow { api.requestToken() }
}