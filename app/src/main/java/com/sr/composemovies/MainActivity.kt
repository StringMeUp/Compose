package com.sr.composemovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
    val vm: MainViewModel = viewModel()
    ComposeMoviesTheme {
        Scaffold(topBar = {
            TopAppBar(
                backgroundColor = Color.Black,
                elevation = 5.dp) {
                Text(text = "Dashboard", color = Color.LightGray)
            }
        }) {
            content()
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MainContent() {
    App {
        Text(text = "This is the main view.")
    }
}
