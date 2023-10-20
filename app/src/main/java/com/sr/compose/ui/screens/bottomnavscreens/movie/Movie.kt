package com.sr.compose.ui.screens.bottomnavscreens.movie

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sr.compose.MainViewModel
import com.sr.compose.ui.theme.ComposeMoviesTheme
import com.sr.compose.ui.widgets.MovieCard
import com.sr.compose.ui.widgets.ProgressIndicator
import timber.log.Timber

@Composable
fun MovieScreen(
    viewModel: MovieViewModel = hiltViewModel(LocalContext.current as ComponentActivity),
    navigateToDetails: (movieId: Int) -> Unit = {},
    onNavigateUp: () -> Unit = {},
) {

    val state = viewModel.movieState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) { viewModel.getMoviesAndGenres() }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 57.dp)
        ) {

            items(state.value.movies) { movie ->
                MovieCard(
                    movie = movie,
                    navigateToDetails = { navigateToDetails(movie.id) },
                    imagePath = { viewModel.getImagePath(movie.id) },
                    genres = { viewModel.getMovieGenres(movie.genreIds) },
                    toggle = { viewModel.toggle(movie.id) }
                )
            }
        }
        if (state.value.isLoading) ProgressIndicator()

        BackHandler(enabled = true) {
            viewModel.clear()
            onNavigateUp()
        }
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