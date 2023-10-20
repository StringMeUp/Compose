package com.sr.compose.repository.usecase

import com.sr.compose.ui.screens.bottomnavscreens.movie.MovieViewModel

abstract class BaseUseCase<in T, out Result> {
    abstract suspend fun getMoviesAndGenres(): Result
    abstract suspend fun getImages(id: Int): Result
}