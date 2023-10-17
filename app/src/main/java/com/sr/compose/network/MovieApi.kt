package com.sr.compose.network

import com.sr.compose.model.GenresResponse
import com.sr.compose.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface MovieApi {

    @Headers("${NetworkConstants.HTTP_HEADER_X_REQUIRES_AUTHORIZATION}: true")
    @GET("/3/movie/popular?language=en-US&page=1")
    suspend fun popular(): Response<MovieResponse>

    @Headers("${NetworkConstants.HTTP_HEADER_X_REQUIRES_AUTHORIZATION}: true")
    @GET("/3/genre/movie/list?language=en")
    suspend fun genres(): Response<GenresResponse>
}

