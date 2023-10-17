package com.sr.compose.ui.screens.bottomnavscreens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sr.compose.MainViewModel
import com.sr.compose.ui.theme.ComposeMoviesTheme
import com.sr.compose.ui.widgets.MovieCard
import com.sr.compose.ui.widgets.ProgressIndicator
import com.sr.compose.util.findGenres

@Composable
fun MovieScreen(
    viewModel: MainViewModel = hiltViewModel(LocalContext.current as ComponentActivity),
    navigateToDetails: (movieId: String) -> Unit = {},
) {
    
    viewModel.fetchMoviesAndGenres()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 57.dp)
        ) {

            items(viewModel.movies.value) {
                MovieCard(
                    movie = it,
                    navigateToDetails = navigateToDetails,
                    imagePath = { viewModel.getImagePath(id = "${it.id}") },
                    genres = { viewModel.genres.value.findGenres(genreIds = it.genreIds) })
            }
        }

        if (viewModel.loading.value) ProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun TestUIPreview() {
    ComposeMoviesTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            MovieCard()
            ProgressIndicator()
        }
    }
}