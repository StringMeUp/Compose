package com.sr.compose.ui.screens.bottomnavscreens.movie

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sr.compose.ui.theme.ComposeMoviesTheme
import com.sr.compose.ui.widgets.MovieCard
import com.sr.compose.ui.widgets.ProgressIndicator
import com.sr.compose.util.helper.bold
import timber.log.Timber

@Composable
fun MovieScreen(
    viewModel: MovieViewModel = hiltViewModel(),
    navigateToDetails: (movieId: Int) -> Unit = {},
) {

    val state = viewModel.movieState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) { viewModel.getMoviesAndGenres() }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            buildAnnotatedString {
                withStyle(bold.copy(fontSize = 24.sp)) { append("Popular Movies") }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Center
        )
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
                    Timber.tag("SHARED").d("${state.value}")
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

        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestUIPreview() {
    ComposeMoviesTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                buildAnnotatedString {
                    withStyle(bold.copy(fontSize = 24.sp)) { append("Popular Movies") }
                },
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                MovieCard()
                ProgressIndicator()
            }
        }
    }
}