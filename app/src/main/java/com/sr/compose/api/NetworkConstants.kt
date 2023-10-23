package com.sr.compose.api

object NetworkConstants {
    const val BASE_URL = "https://api.themoviedb.org"
    const val IMAGE_URL = "https://image.tmdb.org/t/p/original"
    const val AUTH_URL = "authentication-callback"
    const val REDIRECT_URL = "?redirect_to=srcapp://com.sr.compose"

    const val PATH_CONFIRMED = "allow"
    const val PATH_AUTH = "authenticate"
    const val PATH_REDIRECT = "redirect_to"


    const val CRW_TIMEOUT = 10_000L
    const val HTTP_HEADER_X_REQUIRES_AUTHORIZATION = "X-Requires-Authorization"
    const val BEARER = "Bearer"
}
