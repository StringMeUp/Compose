package com.sr.composemovies

import android.os.Parcelable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    @Parcelize
    data class ComposeItem(
        val text: String,
        val image: Int,
    ) : Parcelable {
        companion object {
            fun generate() = listOf<ComposeItem>(
                ComposeItem("Detail: Logo", R.drawable.logo),
                ComposeItem("Detail: Jet", R.drawable.jet),
                ComposeItem("Detail: Ui", R.drawable.ui),
                ComposeItem("Detail: Material", R.drawable.material)
            )
        }
    }

    private var _topBarState = mutableStateOf(true)
    val topBarState get() = _topBarState

    fun setTopBarState(value: Boolean) {
        _topBarState.value = value
    }

    private var _items = mutableStateOf(ComposeItem.generate())
    val items get() = _items
}