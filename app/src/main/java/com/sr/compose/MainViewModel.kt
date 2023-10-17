package com.sr.compose

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sr.compose.model.GenresResponse
import com.sr.compose.model.MovieResponse
import com.sr.compose.navigation.ComposeItem
import com.sr.compose.network.NetworkConstants
import com.sr.compose.repository.MovieRepository
import com.sr.compose.util.Resource
import com.sr.compose.util.evaluateResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MovieRepository,
) : ViewModel() {
    data class MovieGenres(
        val movies: Resource<MovieResponse>,
        val genres: Resource<GenresResponse>,
    )

    init {
        fetchMoviesAndGenres()
    }

    private var _topBarState = mutableStateOf(true)
    val topBarState = _topBarState

    private var _bottomBarState = mutableStateOf(false)
    val bottomBarState = _bottomBarState

    private var _items = mutableStateOf(ComposeItem.generate())
    val items = _items

    private var _movies = MutableStateFlow(emptyList<MovieResponse.Movie>())
    val movies = _movies

    private var _genres = MutableStateFlow(emptyList<GenresResponse.Genre>())
    val genres = _genres

    private fun fetchMoviesAndGenres() {
        viewModelScope.launch {
            val genres = repository.getGenre()
            repository.getPopular().zip(genres) { mov, gnr ->
                MovieGenres(movies = mov, genres = gnr)
            }.collectLatest {
                it.movies.evaluateResource(
                    onLoading = {},
                    onSuccess = { data ->
                        data?.movies?.let {
                            _movies.value = it.sortedWith(compareByDescending { it.voteAverage })
                        }
                    }, onError = {})

                it.genres.evaluateResource(
                    onLoading = {},
                    onSuccess = {
                        it?.genres?.let { _genres.value = it }
                    }, onError = {})
            }
        }
    }

    fun findMovie(id: String): MovieResponse.Movie? {
        return _movies.value.find { it.id == id.toInt() }
    }

    fun getImagePath(id: String): String {
        return "${NetworkConstants.IMAGE_URL}${findMovie(id)?.posterPath}"
    }

    /**--------------------------------- Top/Bottom Bar ------------------------------------*/

    fun setTopBarState(value: Boolean) {
        _topBarState.value = value
    }

    fun setBottomBarState(value: Boolean) {
        _bottomBarState.value = value
    }
}