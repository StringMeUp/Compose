import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.sr.composemovies.MainViewModel
import com.sr.composemovies.NavigationConstants
import com.sr.composemovies.customSerializable
import com.sr.composemovies.navigation.NavigationItem
import com.sr.composemovies.ui.screens.HomeScreen
import com.sr.composemovies.ui.screens.ArgumentsScreen
import com.sr.composemovies.ui.screens.NextScreen
import com.sr.composemovies.ui.screens.SerializableScreen

//function to navigate with a navController
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavigation(
    navController: NavHostController,
    viewModel: MainViewModel = viewModel(),
) {
    //navHost with start destination
    AnimatedNavHost(navController = navController, startDestination = NavigationItem.Main.route) {
        composable(
            route = NavigationItem.Main.route,
            enterTransition = {
                when (initialState.destination.route) {
                    NavigationItem.Detail.route ->
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tween(500))
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    NavigationItem.Detail.route ->
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tween(500))
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    NavigationItem.Detail.route ->
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(500))
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    NavigationItem.Detail.route ->
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(500))
                    else -> null
                }
            }
        ) {
            HomeScreen(navController = navController)
        }

        composable(
            route = NavigationItem.Detail.route,
            arguments = listOf(
                navArgument(name = NavigationConstants.Arg_Detail) {
                    type = NavType.StringType
                    defaultValue = "Default"
                }
            ),
            enterTransition = {
                when (initialState.destination.route) {
                    NavigationItem.Main.route ->
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tween(500))
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    NavigationItem.Main.route ->
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tween(500))
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    NavigationItem.Main.route ->
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(500))
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    NavigationItem.Main.route ->
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(500))
                    else -> null
                }
            }) { backStackEntry ->
            ArgumentsScreen(navController = navController,
                value = backStackEntry.arguments?.getString(NavigationConstants.Arg_Detail))
        }

        composable(route = NavigationItem.Next.route, arguments = listOf(
            navArgument(name = NavigationConstants.Arg_Next) {
                type = NavType.StringType
                nullable = true
            })) { backStackEntry ->
            NextScreen(
                navController = navController,
                args = backStackEntry.arguments?.getString(NavigationConstants.Arg_Next))
        }

        composable(route = NavigationItem.Serializable.route, arguments = listOf(
            navArgument(name = NavigationConstants.Arg_Serial) {
                type = NavType.StringType
                nullable = true
            })) { backStackEntry ->
            SerializableScreen(
                navController = navController,
                args = backStackEntry.customSerializable(NavigationConstants.Arg_Serial))
        }

        /** Jetpack Compose without animations::
        composable(route = NavigationItem.Main.route) {
        MainScreen(navController = navController)
        }
        composable(route = NavigationItem.Detail.route) {
        DetailScreen()
        }*/
    }
}

inline fun Modifier.clickWithDebounce(
    debounceInterval: Long = 700,
    crossinline onClick: () -> Unit,
): Modifier {
    var lastClickTime = 0L
    val currentTime = System.currentTimeMillis()
    return clickable {
        if ((currentTime - lastClickTime) < debounceInterval) return@clickable
        lastClickTime = currentTime
        onClick()
    }
}