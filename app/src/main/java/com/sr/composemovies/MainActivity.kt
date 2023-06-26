package com.sr.composemovies

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sr.composemovies.ui.theme.ComposeMoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App {
                MainContent()
            }
        }
    }
}

@Composable
fun App(content: @Composable () -> Unit) {
    ComposeMoviesTheme {
        Scaffold(topBar = {
            TopAppBar(backgroundColor = Color.Black, elevation = 5.dp) {
                Text(text = "Movies", color = Color.LightGray)
            }
        }) {
            content()
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MainContent(viewModel: MainViewModel = viewModel()) {
    Movies(viewModel)
}

@Composable
fun Movies(viewModel: MainViewModel) {
    LazyColumn {
        items(viewModel.getMovies()) {
            MovieRow(movie = it)
        }
    }
}

@Composable
fun MovieRow(movie: String) {
    Card(modifier = Modifier
        .height(130.dp)
        .fillMaxWidth()
        .padding(12.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp) {
        Row(horizontalArrangement = Arrangement.Start) {
            Image(
                painterResource(id = R.drawable.ic_baseline_self_improvement_24),
                "Movie image.", modifier = Modifier
                    .fillMaxHeight()
                    .width(100.dp),
                contentScale = ContentScale.FillHeight)
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            Text(text = movie)
        }
    }
}


