package com.sr.composemovies

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    fun getMovies() = listOf<String>(
        "S. Redemption",
        "Spiderman",
        "Batman",
        "See-man",
        "J. Park",
        "The Godfather",
        "12 Angry Men",
        "Shindler's List", "S. Redemption",
        "Spiderman",
        "Batman",
        "See-man",
        "J. Park",
        "The Godfather",
        "12 Angry Men",
        "Shindler's List"
    )
}