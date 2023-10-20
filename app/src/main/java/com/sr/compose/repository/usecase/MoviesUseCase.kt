package com.sr.compose.repository.usecase

import com.sr.compose.repository.MovieRepository
import com.sr.compose.ui.screens.bottomnavscreens.movie.MovieViewModel
import com.sr.compose.util.Resource
import com.sr.compose.util.evaluateResource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

class MoviesUseCase @Inject constructor(private val repository: MovieRepository) :
    BaseUseCase<Unit, MovieViewModel.MovieState>() {
    private var movieUiState = MovieViewModel.MovieState()

    override suspend fun getMoviesAndGenres(): MovieViewModel.MovieState {
        repository.apply {
            getPopular().zip(getGenre()) { mov, gnr ->
                Pair(mov, gnr)
            }.onCompletion { movieUiState = movieUiState.copy(isLoading = false) }
                .collectLatest {
                    val (movies, genres) = it
                    movies.evaluateResource(onSuccess = { data ->
                        data?.movies?.let { movies ->
                            val sortedMovies =
                                movies.sortedWith(compareByDescending { it.voteAverage })
                            movieUiState = movieUiState.copy(movies = sortedMovies)
                        }
                    }, onError = {/*todo*/ })

                    genres.evaluateResource(onSuccess = { all ->
                        all?.genres?.let { movieUiState = movieUiState.copy(genres = it) }
                    }, onError = {/*todo*/ })
                }
        }
        return movieUiState
    }

    override suspend fun getImages(id: Int): MovieViewModel.MovieState {
        repository.getImages(id).collectLatest() {
            it.evaluateResource(
                onSuccess = { response ->
                    response?.let { image ->
                        movieUiState = movieUiState.copy(images = image.backdrops)
                    }
                },
                onError = {/*todo*/ }
            )
        }
        return movieUiState
    }
}