package com.sr.composemovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sr.composemovies.navigation.Navigation
import com.sr.composemovies.ui.theme.ComposeMoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App {
               Navigation()
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


