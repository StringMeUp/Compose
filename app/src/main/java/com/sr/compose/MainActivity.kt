package com.sr.compose

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.util.Consumer
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sr.compose.navigation.AppNavigation
import com.sr.compose.navigation.ComposeItem
import com.sr.compose.navigation.DeepLinkVm
import com.sr.compose.navigation.NavigationItem
import com.sr.compose.ui.theme.ComposeMoviesTheme
import com.sr.compose.ui.widgets.BottomBar
import com.sr.compose.ui.widgets.TopBar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val deepLinkProcessingViewModel = viewModel<DeepLinkVm>()
            val startDestination = rememberSaveable(context) {
                val deepLink =
                    deepLinkProcessingViewModel.processDeepLinkIfAvailable(intent = intent)
                if (deepLink?.startsWith("srcapp") == true) {
                    // deep link handled
                    NavigationItem.BottomNavMain.BottomNavProfile.route
                } else {
                    // default start destination
                    NavigationItem.BottomNavMain.route
                }
            }
            navController = rememberNavController()

            Timber.tag("SAMIRRAMIC")
                .d("Navigate to current destination:: ${navController.currentDestination}")
            DisposableEffect(navController) {
                val consumer = Consumer<Intent> {
                    navController.handleDeepLink(it)
                }
                this@MainActivity.addOnNewIntentListener(consumer)
                onDispose {
                    this@MainActivity.removeOnNewIntentListener(consumer)
                }
            }
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val viewModel: MainViewModel = hiltViewModel(LocalContext.current as ComponentActivity)

            ComposeApp(
                isTopBarVisible = { viewModel.topBarState.value },
                isBottomBarVisible = { viewModel.bottomBarState.value },
                setTopBar = { isVisible -> viewModel.setTopBarState(isVisible) },
                setBottomBar = { isVisible -> viewModel.setBottomBarState(isVisible) },
                composeItems = { viewModel.items.value },
                navController = navController,
                navBackStackEntry = navBackStackEntry,
                startDestination = startDestination
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
        setBottomBar(
            NavigationItem.BottomNavMain?.bottomNavDestinations()
                ?.any { it.route == currentRoute } ?: false)
    }
}


