package com.sr.composemovies.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sr.composemovies.MainViewModel
import com.sr.composemovies.R
import com.sr.composemovies.navigation.Destination

@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel = viewModel()) {
    LazyColumn {
        items(viewModel.getMovies()) {
            MovieRow(movie = it) { movie ->
                navController.navigate(Destination.Detail.route)
            }
        }
    }
}

@Composable
fun MovieRow(movie: String, onItemClick: (String) -> Unit) {
    Card(modifier = Modifier
        .height(130.dp)
        .fillMaxWidth()
        .padding(12.dp)
        .clickable {
            onItemClick(movie)
        },
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp) {
        Row(horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {
            Image(
                painterResource(id = R.drawable.ic_baseline_self_improvement_24),
                "Movie image.", modifier = Modifier
                    .fillMaxHeight()
                    .width(100.dp),
                contentScale = ContentScale.FillHeight)
        }

        Text(text = movie,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth())
    }
}