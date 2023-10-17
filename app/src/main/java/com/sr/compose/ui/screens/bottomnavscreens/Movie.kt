package com.sr.compose.ui.screens.bottomnavscreens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sr.compose.MainViewModel
import com.sr.compose.ui.theme.ComposeMoviesTheme
import com.sr.compose.ui.widgets.MovieCard

@Composable
fun MovieScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navigateToDetails: (movieId: String) -> Unit = {},
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 57.dp)
    ) {
        items(viewModel.movies.value) {
            MovieCard(it, navigateToDetails)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestUIPreview() {
    ComposeMoviesTheme {
        MovieCard()
    }
}