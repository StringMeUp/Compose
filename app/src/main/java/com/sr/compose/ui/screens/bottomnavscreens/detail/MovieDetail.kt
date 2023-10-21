package com.sr.compose.ui.screens.bottomnavscreens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.sr.compose.R
import com.sr.compose.model.ImagesResponse
import com.sr.compose.ui.helper.bold
import com.sr.compose.ui.screens.bottomnavscreens.movie.MovieViewModel
import com.sr.compose.ui.widgets.MovieCard

@Composable
@Preview
fun MovieDetailScreen(
    args: Int = -1,
    viewModel: MovieViewModel = hiltViewModel(),
) {
    val movie = checkNotNull((viewModel::findMovie)(args))
    val state = viewModel.movieState.collectAsStateWithLifecycle()

    viewModel.getImages(movieId = movie.id)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            buildAnnotatedString {
                withStyle(bold.copy(fontSize = 24.sp)) { append(movie.title) }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Center
        )

        ImagesRow(images = state.value.images) { viewModel.getBackdrop(it) }
        Divider(modifier = Modifier.height(24.dp), color = Color.Transparent)
        MovieCard(movie = movie,
            imagePath = { viewModel.getImagePath(movie.id) },
            genres = { viewModel.getMovieGenres(movie.genreIds) },
            toggle = { viewModel.toggle(movie.id) })
    }

}

@Composable
private fun ImagesRow(
    images: List<ImagesResponse.MovieImage>,
    path: (filePath: String) -> String = { "" },
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(images) { image ->
            AsyncImage(
                placeholder = painterResource(R.drawable.ic_movie),
                model = path(image.filePath),
                contentDescription = "Movie image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(250.dp, 200.dp)
                    .padding(12.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
}