package com.sr.compose.domain.usecase

abstract class BaseUseCase<in T, out Result> where Result : Any {
    open suspend fun getMoviesAndGenres(): Any = Any()
    open suspend fun getImages(id: Int): Any = Any()
    open suspend fun getRequestToken(): Any = Any()
}