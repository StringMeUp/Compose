package com.sr.compose.ui.screens.bottomnavscreens.detail

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.sr.compose.MainViewModel
import com.sr.compose.R
import com.sr.compose.model.ImagesResponse
import com.sr.compose.ui.widgets.MovieCard

@Composable
@Preview
fun MovieDetailScreen(
    args: Int = -1,
    viewModel: MainViewModel = hiltViewModel(LocalContext.current as ComponentActivity),
) {
    val movie = checkNotNull((viewModel::findMovie)(args))
    val state = viewModel.userCurrentState.collectAsStateWithLifecycle(
        initialValue = MainViewModel.UIState(emptyList())
    )

    viewModel.getImages(movieId = movie.id)

    Column(modifier = Modifier.fillMaxSize()) {
        ImagesRow(images = state.value.images) { viewModel.getBackdrop(it) }

        Divider(
            modifier = Modifier.height(24.dp), color = Color.Transparent
        )

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
                contentDescription = "Image of ",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(250.dp, 200.dp)
                    .padding(12.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
}