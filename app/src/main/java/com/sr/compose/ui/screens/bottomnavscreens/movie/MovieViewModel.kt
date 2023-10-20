package com.sr.compose.ui.screens.bottomnavscreens.movie

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sr.compose.model.GenresResponse
import com.sr.compose.model.ImagesResponse
import com.sr.compose.model.MovieResponse
import com.sr.compose.network.NetworkConstants
import com.sr.compose.repository.usecase.MoviesUseCase
import com.sr.compose.util.findGenres
import com.sr.compose.util.toggleVisibility
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val useCase: MoviesUseCase,
) : ViewModel() {

    data class MovieState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val movies: List<MovieResponse.Movie> = emptyList(),
        val genres: List<GenresResponse.Genre> = emptyList(),
        val images: List<ImagesResponse.MovieImage> = emptyList(),
    )

    private val _movieState: MutableStateFlow<MovieState> = MutableStateFlow(MovieState())
    val movieState: StateFlow<MovieState> = _movieState

    fun getMoviesAndGenres() {
        if (movieState.value.movies.isEmpty())
        viewModelScope.launch {
            _movieState.update {
                useCase.getMoviesAndGenres()
            }
        }
    }

    fun getImages(movieId: Int) {
        viewModelScope.launch {
            _movieState.update {
                it.copy(images = useCase.getImages(movieId).images)
            }
        }
    }

    fun findMovie(id: Int): MovieResponse.Movie? {
        return _movieState.value.movies.find { it.id == id }
    }

    fun getImagePath(id: Int): String {
        return "${NetworkConstants.IMAGE_URL}${findMovie(id)?.posterPath}"
    }

    fun getBackdrop(path: String): String {
        return "${NetworkConstants.IMAGE_URL}${path}"
    }

    fun getMovieGenres(genreIds: List<Int>) =
        _movieState.value.genres.findGenres(genreIds = genreIds)

    fun toggle(id: Int) {
        _movieState.update {
            it.copy(movies = it.movies.toggleVisibility { (this::findMovie)(id) })
        }
    }

    fun clear(){
        _movieState.update { MovieState() }
    }
}