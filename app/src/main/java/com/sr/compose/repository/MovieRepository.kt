package com.sr.compose.repository

import com.sr.compose.network.MovieApi
import com.sr.compose.util.RemoteSource
import javax.inject.Inject

class MovieRepository @Inject constructor(private val api: MovieApi) {

    fun getPopular() = RemoteSource.launchResultFlow { api.popular() }
    fun getGenre() = RemoteSource.launchResultFlow { api.genres() }
}