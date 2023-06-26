package com.sr.composemovies.ui.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.sr.composemovies.MainViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
@Preview
fun DetailScreen(viewModel: MainViewModel = viewModel()) {
    Text(text = "Hello detail", color = Color.Black)
}