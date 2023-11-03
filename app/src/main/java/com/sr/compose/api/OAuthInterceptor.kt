package com.sr.compose.api

import com.sr.compose.BuildConfig
import com.sr.compose.api.NetworkConstants
import com.sr.compose.api.NetworkConstants.HTTP_HEADER_X_REQUIRES_AUTHORIZATION
import okhttp3.Interceptor

class OAuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        val isAuthRequest = request.header(HTTP_HEADER_X_REQUIRES_AUTHORIZATION) != null

        if (isAuthRequest) request = request.newBuilder()
            .header(
                name = NetworkConstants.HTTP_HEADER_AUTHORIZATION,
                value = "${NetworkConstants.BEARER} ${BuildConfig.TMDB_API_KEY}"
            ).build()

        return chain.proceed(request)
    }
}