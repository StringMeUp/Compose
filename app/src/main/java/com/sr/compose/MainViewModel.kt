package com.sr.compose

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sr.compose.model.GenresResponse
import com.sr.compose.model.ImagesResponse
import com.sr.compose.model.MovieResponse
import com.sr.compose.navigation.ComposeItem
import com.sr.compose.network.NetworkConstants
import com.sr.compose.repository.MovieRepository
import com.sr.compose.util.Resource
import com.sr.compose.util.evaluateResource
import com.sr.compose.util.findGenres
import com.sr.compose.util.toggleVisibility
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.update
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

    private var _topBarState = mutableStateOf(true)
    val topBarState = _topBarState

    private var _bottomBarState = mutableStateOf(false)
    val bottomBarState = _bottomBarState

    private var _items = mutableStateOf(ComposeItem.generate())
    val items = _items

    private var _movies = mutableStateOf(emptyList<MovieResponse.Movie>())
    val movies = _movies

    private var _genres = mutableStateOf(emptyList<GenresResponse.Genre>())
    val genres = _genres

    private var _error = mutableStateOf(false)
    val error = _error

    private var _loading = mutableStateOf(true)
    val loading = _loading

    data class UIState(val images: List<ImagesResponse.MovieImage>)

    private val mutableUserState: MutableStateFlow<UIState> =
        MutableStateFlow(UIState(emptyList()))

    val userCurrentState: StateFlow<UIState> =
        mutableUserState

    fun getMoviesAndGenres() {
        if (_movies.value.isEmpty())
            viewModelScope.launch {
                repository.apply {
                    getPopular().zip(getGenre()) { mov, gnr ->
                        MovieGenres(movies = mov, genres = gnr)
                    }.onCompletion { _loading.value = false }
                        .collectLatest {
                            it.movies.evaluateResource(
                                onSuccess = { data ->
                                    data?.movies?.let {
                                        _movies.value =
                                            it.sortedWith(compareByDescending { it.voteAverage })
                                    }
                                }, onError = {})

                            it.genres.evaluateResource(
                                onSuccess = {
                                    it?.genres?.let { _genres.value = it }
                                }, onError = {})
                        }
                }
            }
    }

    fun getImages(movieId: Int) {
        viewModelScope.launch {
            repository.getImages(movieId).collect() {
                if (it is Resource.Success) {
                    mutableUserState.update { old ->
                        old.copy(images = it.data?.backdrops!!)
                    }
                }
            }
        }
    }

    fun findMovie(id: Int): MovieResponse.Movie? {
        return _movies.value.find { it.id == id }
    }

    fun getImagePath(id: Int): String {
        return "${NetworkConstants.IMAGE_URL}${findMovie(id)?.posterPath}"
    }

    fun getBackdrop(path: String): String {
        return "${NetworkConstants.IMAGE_URL}${path}"
    }


    fun getMovieGenres(genreIds: List<Int>) = genres.value.findGenres(genreIds = genreIds)

    fun toggle(id: Int) {
        _movies.value = _movies.value.toggleVisibility { (this::findMovie)(id) }
    }

    /**--------------------------------- Top/Bottom Bar ------------------------------------*/

    fun setTopBarState(value: Boolean) {
        _topBarState.value = value
    }

    fun setBottomBarState(value: Boolean) {
        _bottomBarState.value = value
    }
}