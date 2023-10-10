package com.sr.compose

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.sr.compose.model.getMovies
import com.sr.compose.navigation.ComposeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val savedStateHandle: SavedStateHandle) : ViewModel() {

    private var _topBarState = mutableStateOf(true)
    val topBarState = _topBarState

    private var _bottomBarState = mutableStateOf(false)
    val bottomBarState = _bottomBarState

    private var _items = mutableStateOf(ComposeItem.generate())
    val items = _items

    private var _movies = savedStateHandle.getStateFlow(key = "Movies", initialValue = getMovies())
    val movies = _movies

    fun setTopBarState(value: Boolean) {
        _topBarState.value = value
    }

    fun setBottomBarState(value: Boolean) {
        _bottomBarState.value = value
    }
}