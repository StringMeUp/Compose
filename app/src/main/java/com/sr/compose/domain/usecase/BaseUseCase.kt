package com.sr.compose.domain.usecase

abstract class BaseUseCase<in T, out Result> {
    abstract suspend fun getMoviesAndGenres(): Result
    abstract suspend fun getImages(id: Int): Result
}