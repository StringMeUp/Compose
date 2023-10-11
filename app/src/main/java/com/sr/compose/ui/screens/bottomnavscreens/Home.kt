package com.sr.compose.ui.screens.bottomnavscreens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sr.compose.MainViewModel
import com.sr.compose.R
import com.sr.compose.model.Movie
import com.sr.compose.model.getMovies
import com.sr.compose.ui.helper.StyledText
import com.sr.compose.ui.theme.ComposeMoviesTheme

@Composable
fun MovieScreen(
    viewModel: MainViewModel = viewModel(),
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 57.dp)
    ) {
        items(viewModel.movies.value) {
            MovieCard(it)
        }
    }
}

@Composable
private fun MovieCard(movie: Movie) {
    val isVisible = rememberSaveable { mutableStateOf(false) }

    val bold = SpanStyle(
        color = Color.Black,
        fontSize = 16.sp,
        fontFamily = FontFamily.Monospace
    )
    val gray = SpanStyle(
        color = Color.Gray,
        fontFamily = FontFamily.Monospace
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(5.dp)
            .clip(shape = RoundedCornerShape(8.dp))
    ) {
        Row {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .padding(6.dp)
                    .clip(CircleShape)
            ) {
                Image(
                    painterResource(R.drawable.all_night),
                    contentScale = ContentScale.Crop,
                    contentDescription = "poster"
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    StyledText(
                        text1 = "Title",
                        text2 = movie.title,
                        color1 = Color.Black,
                        color2 = Color.Gray
                    )

                    Icon(
                        imageVector = if (isVisible.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = "Down Arrow",
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .size(25.dp)
                            .clickable {
                                isVisible.value = !isVisible.value
                            },
                        tint = Color.DarkGray
                    )
                }

                Text(buildAnnotatedString {
                    withStyle(bold) { append("Genre:") }
                    withStyle(gray) { append("${movie.genre}\n") }
                    withStyle(bold) { append("Director:") }
                    withStyle(gray) { append("${movie.director}\n") }
                }, modifier = Modifier.padding(6.dp))

                AnimatedVisibility(visible = isVisible.value) {
                    Column {
                        Text(buildAnnotatedString {
                            withStyle(bold) { append("Plot:") }
                            withStyle(gray) { append("${movie.plot}\n") }
                        })
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestUIPreview() {
    ComposeMoviesTheme {
        MovieCard(movie = getMovies().first())
    }
}