package com.sr.compose.util

import com.sr.compose.model.GenresResponse

fun <T>Resource<T>.evaluateResource(

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