package com.sr.compose.api

import com.sr.compose.model.GenresResponse
import com.sr.compose.model.ImagesResponse
import com.sr.compose.model.MovieResponse
import com.sr.compose.model.auth.RequestTokenResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface MovieApi {

    @Headers("${NetworkConstants.HTTP_HEADER_X_REQUIRES_AUTHORIZATION}: true")
    @GET("/3/movie/popular?language=en-US&page=1")
    suspend fun popular(): Response<MovieResponse>

    @Headers("${NetworkConstants.HTTP_HEADER_X_REQUIRES_AUTHORIZATION}: true")
    @GET("/3/genre/movie/list?language=en")
    suspend fun genres(): Response<GenresResponse>

    @Headers("${NetworkConstants.HTTP_HEADER_X_REQUIRES_AUTHORIZATION}: true")
    @GET("/3/movie/{movie_id}/images")
    suspend fun images(@Path("movie_id") movieId: Int): Response<ImagesResponse>

    @Headers("${NetworkConstants.HTTP_HEADER_X_REQUIRES_AUTHORIZATION}: true")
    @GET("/3/authentication/token/new")
    suspend fun requestToken(): Response<RequestTokenResponse>

}

