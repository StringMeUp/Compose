package com.sr.compose

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sr.compose.navigation.AppNavigation
import com.sr.compose.navigation.ComposeItem
import com.sr.compose.navigation.DeepLinkViewModel
import com.sr.compose.navigation.NavigationConstants
import com.sr.compose.navigation.NavigationItem
import com.sr.compose.ui.theme.ComposeMoviesTheme
import com.sr.compose.ui.widgets.BottomBar
import com.sr.compose.ui.widgets.TopBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            val deepLinkHandler = viewModel<DeepLinkViewModel>()
            val viewModel: MainViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
            val navBackStackEntry by navController.currentBackStackEntryAsState()

            DeepLinkBottomNavigation(deepLinkHandler, navController, intent)
            ComposeApp(
                isTopBarVisible = { viewModel.topBarState.value },
                isBottomBarVisible = { viewModel.bottomBarState.value },
                setTopBar = { isVisible -> viewModel.setTopBarState(isVisible) },
                setBottomBar = { isVisible -> viewModel.setBottomBarState(isVisible) },
                composeItems = { viewModel.items.value },
                navController = navController,
                navBackStackEntry = navBackStackEntry,
                startDestination = NavigationItem.Main.route
            )
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@Preview
fun ComposeApp(
    isTopBarVisible: () -> Boolean = { false },
    isBottomBarVisible: () -> Boolean = { false },
    setTopBar: (isVisible: Boolean) -> Unit = {},
    setBottomBar: (isVisible: Boolean) -> Unit = {},
    composeItems: () -> List<ComposeItem> = { ComposeItem.generate() },
    navController: NavHostController = rememberNavController(),
    navBackStackEntry: NavBackStackEntry? = navController.currentBackStackEntry,
    startDestination: String? = "",
) {
    ComposeMoviesTheme {
        Scaffold(
            bottomBar = {
                BottomBar(
                    currentRoute = navBackStackEntry?.destination?.route,
                    isVisible = isBottomBarVisible(),
                    navController = navController
                )
            }) {
            AppNavigation(
                navController = navController,
                composeItems = { composeItems() },
                startDestination = startDestination
            )
            TopBar(isTopBarVisible, navController)
            /** Only way to make the detail views not jump is to exclude [TopBar] from Scaffold. */
        }

        val currentRoute = navBackStackEntry?.destination?.route
        setTopBar(currentRoute == NavigationItem.Main.route)
        setBottomBar(NavigationItem.BottomNavMain?.bottomNavDestinations()?.any { it.route == currentRoute } ?: false)
    }
}

@Composable
private fun DeepLinkBottomNavigation(
    deepLinkHandler: DeepLinkViewModel,
    navController: NavHostController,
    intent: Intent,
) {
    LaunchedEffect(Unit) {
        /** Manually handling deep-linking because of the nested bottom navigation. */
        val deepLink = deepLinkHandler.processDeepLink(intent) ?: return@LaunchedEffect
        if (deepLink.startsWith(NavigationConstants.Scheme)) {
            navController.navigate(
                deepLink = deepLink.toUri(),
                navOptions = NavOptions.Builder()
                    .setPopUpTo(route = NavigationItem.BottomNavMain.route, true)
                    .build()

            )
        }
    }
}