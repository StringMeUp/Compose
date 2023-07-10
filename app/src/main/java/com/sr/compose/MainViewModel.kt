package com.sr.compose

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.sr.compose.navigation.ComposeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val savedStateHandle: SavedStateHandle) : ViewModel() {

    private var _topBarState = mutableStateOf(true)
    val topBarState get() = _topBarState

    fun setTopBarState(value: Boolean) {
        _topBarState.value = value
    }

    private var _items = mutableStateOf(ComposeItem.generate())
    val items get() = _items
}