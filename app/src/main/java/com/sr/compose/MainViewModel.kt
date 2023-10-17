package com.sr.compose

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.sr.compose.model.MovieResponse
import com.sr.compose.model.getMovies
import com.sr.compose.navigation.ComposeItem
import com.sr.compose.repository.MovieRepository
import com.sr.compose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    val repository: MovieRepository,
) : ViewModel() {

    init {
        getPopular()
    }

    private var _topBarState = mutableStateOf(true)
    val topBarState = _topBarState

    private var _bottomBarState = mutableStateOf(false)
    val bottomBarState = _bottomBarState

    private var _items = mutableStateOf(ComposeItem.generate())
    val items = _items

    private var _movies = MutableStateFlow(emptyList<MovieResponse.Movie>())
    val movies = _movies

    fun getPopular() {
        viewModelScope.launch {
            repository.getPopular().collect { res ->
                when (res) {
                    Resource.Loading -> {}
                    is Resource.Error -> {}
                    is Resource.Success -> {
                        res.data?.movies?.let { _movies.value = it }
                    }
                }

            }
        }
    }

    fun findMovie(id: String): MovieResponse.Movie? {
        return _movies.value.find { it.id == id.toInt() }
    }

    fun setTopBarState(value: Boolean) {
        _topBarState.value = value
    }

    fun setBottomBarState(value: Boolean) {
        _bottomBarState.value = value
    }
}