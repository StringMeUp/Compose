package com.sr.compose.util

import com.sr.compose.domain.Resource
import com.sr.compose.model.GenresResponse
import com.sr.compose.model.MovieResponse

/** Resource */
fun <T> Resource<T>.evaluateResource(
    onLoading: () -> Unit = {},
    onSuccess: (data: T?) -> Unit = {},
    onSuccessAuth: (data: T?, url: String?) -> Unit = { _, _ -> },
    onError: () -> Unit,
): Unit = when (this) {
    Resource.Loading -> {
        onLoading()
    }

    is Resource.Error -> {
        onError()
    }

    is Resource.Success -> {
        onSuccessAuth(data, authUrl())
        onSuccess(data)
    }
}

/** List:: Movies */
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