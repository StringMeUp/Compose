package com.sr.compose.domain.repository

import com.sr.compose.api.MovieApi
import com.sr.compose.domain.RemoteSource
import javax.inject.Inject

class MovieRepository @Inject constructor(private val api: MovieApi) {
    fun getPopular() = RemoteSource.launchResultFlow { api.popular() }
    fun getGenre() = RemoteSource.launchResultFlow { api.genres() }
    fun getImages(movieId: Int) = RemoteSource.launchResultFlow { api.images(movieId) }
}