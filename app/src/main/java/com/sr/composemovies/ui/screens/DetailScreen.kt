package com.sr.composemovies.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sr.composemovies.MainViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
@Preview(showBackground = true)
fun DetailScreen(viewModel: MainViewModel = viewModel()) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Text(text = "Hello detail", color = Color.Black)
    }
}