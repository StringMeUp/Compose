package com.sr.compose.util

import com.sr.compose.model.GenresResponse
import com.sr.compose.model.MovieResponse

fun <T> Resource<T>.evaluateResource(
    onLoading: () -> Unit = {},
    onSuccess: (data: T?) -> Unit,
    onError: () -> Unit,
): Unit = when (this) {
    Resource.Loading -> {
        onLoading()
    }

    is Resource.Error -> {
        onError()
    }

    is Resource.Success -> {
        onSuccess(this.data)
    }
}

fun List<GenresResponse.Genre>.findGenres(genreIds: List<Int>): String {
    val filtered = this.filter { genre -> genreIds.any { genre.id == it } }.map { it.name }
    return filtered.joinToString()
}

fun List<MovieResponse.Movie>.toggleVisibility(findMovie: () -> MovieResponse.Movie?): List<MovieResponse.Movie> {
    return this.map {
        val movie = findMovie()
        if (movie?.id == it.id) {
            movie.copy(isVisible = !movie.isVisible)
        } else {
            it
        }
    }
}