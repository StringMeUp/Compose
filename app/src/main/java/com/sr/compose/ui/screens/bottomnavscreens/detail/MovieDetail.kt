package com.sr.compose.ui.screens.bottomnavscreens.detail

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sr.compose.MainViewModel
import com.sr.compose.model.MovieResponse
import com.sr.compose.ui.theme.ComposeMoviesTheme
import com.sr.compose.ui.widgets.MovieCard
import com.sr.compose.util.findGenres

@Composable
fun MovieDetailScreen(
    args: String = "",
    viewModel: MainViewModel = hiltViewModel(LocalContext.current as ComponentActivity),
) {
    val movie = (viewModel::findMovie)(args)
    MovieDetails(movie)
}

@Composable
fun MovieDetails(
    movie: MovieResponse.Movie?,
    viewModel: MainViewModel = hiltViewModel(LocalContext.current as ComponentActivity),
) {
    Column(modifier = Modifier.fillMaxSize()) {
        movie?.let {
            MovieCard(
                movie = it,
                imagePath = { viewModel.getImagePath("${it.id}") },
                genres = { viewModel.genres.value.findGenres(genreIds = it.genreIds) })
            Divider(
                modifier = Modifier.height(24.dp), color = Color.Transparent
            )
            Text(
                buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    ) {
                        append("Images for: ")
                    }
                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Normal,
                            fontFamily = FontFamily.Monospace
                        )
                    ) {
                        append(it.title)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

//            LazyRow(
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                items(it.images) { resource ->
//                    AsyncImage(
//                        placeholder = painterResource(R.drawable.ic_movie),
//                        model = resource,
//                        contentDescription = "Image of ${movie.title}",
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier
//                            .size(250.dp, 200.dp)
//                            .padding(12.dp)
//                            .clip(RoundedCornerShape(8.dp))
//
//                    )
//                }
//            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestUIPreview() {
    ComposeMoviesTheme {
        MovieDetails(null)
    }
}