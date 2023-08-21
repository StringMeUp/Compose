package com.sr.compose

import com.sr.compose.navigation.AppNavigation
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.accompanist.insets.ui.Scaffold
import androidx.compose.animation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.sr.compose.navigation.ComposeItem

import com.sr.compose.navigation.NavigationItem
import com.sr.compose.ui.theme.ComposeMoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = viewModel()
            MoviesApp(
                isTopBarVisible = { viewModel.topBarState.value },
                isBottomBarVisible = { viewModel.bottomBarState.value },
                setTopBar = { isVisible -> viewModel.setTopBarState(isVisible) },
                setBottomBar = { isVisible -> viewModel.setBottomBarState(isVisible) },
                composeItems = { viewModel.items.value })
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalAnimationApi::class)
@Composable
@Preview
fun MoviesApp(
    isTopBarVisible: () -> Boolean = { false },
    isBottomBarVisible: () -> Boolean = { false },
    setTopBar: (isVisible: Boolean) -> Unit = {},
    setBottomBar: (isVisible: Boolean) -> Unit = {},
    composeItems: () -> List<ComposeItem> = { ComposeItem.generate() },
) {
    ComposeMoviesTheme {
        val navController = rememberAnimatedNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        Scaffold(
            topBar = {
                TopBar(isTopBarVisible, navController)
            },
            bottomBar = {
                BottomBar(
                    currentRoute = navBackStackEntry?.destination?.route,
                    isVisible = isBottomBarVisible(),
                    navController = navController
                )
            }) {
            Column {
                AppNavigation(navController = navController, composeItems = { composeItems() })
            }
        }

        val currentRoute = navBackStackEntry?.destination?.route
        setTopBar(currentRoute == NavigationItem.Main.route)
        setBottomBar(NavigationItem.BottomMain?.bottomNavDestinations()?.any { it.route == currentRoute } ?: false)
    }
}


