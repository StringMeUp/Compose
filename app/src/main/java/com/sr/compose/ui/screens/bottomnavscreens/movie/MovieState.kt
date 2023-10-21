package com.sr.compose.ui.screens.bottomnavscreens.movie

import android.os.Parcelable
import com.sr.compose.model.GenresResponse
import com.sr.compose.model.ImagesResponse
import com.sr.compose.model.MovieResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val movies: List<MovieResponse.Movie> = emptyList(),
    val genres: List<GenresResponse.Genre> = emptyList(),
    val images: List<ImagesResponse.MovieImage> = emptyList(),
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        return if (other is MovieState) {
            isLoading == other.isLoading &&
                    isError == other.isError &&
                    movies == other.movies &&
                    genres == other.genres &&
                    images == other.images
        } else {
            super.equals(other)
        }
    }

    override fun hashCode(): Int {
        var result = isLoading.hashCode()
        result = 31 * result + isError.hashCode()
        result = 31 * result + movies.hashCode()
        result = 31 * result + genres.hashCode()
        result = 31 * result + images.hashCode()
        return result
    }
}