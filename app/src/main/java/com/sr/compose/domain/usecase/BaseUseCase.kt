package com.sr.compose.domain.usecase

abstract class BaseUseCase<in T, out Result> where Result : Any {
    open suspend fun getMoviesAndGenres(): Any {
        return Any()
    }

    open suspend fun getImages(id: Int): Any {
        return Any()
    }

    open suspend fun getRequestToken(): Any {
        return Any()
    }
}