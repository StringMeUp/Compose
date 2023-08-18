package com.sr.compose.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.sr.compose.MainViewModel
import com.sr.compose.NavigationConstants
import com.sr.compose.R
import com.sr.compose.customSerializable
import com.sr.compose.ui.screens.*
import com.sr.compose.withCustomSerializable

//function to navigate with a navController
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavigation(
    navController: NavHostController,
    composeItems: () -> List<ComposeItem> = { emptyList() },
) {
    //navHost with start destination
    AnimatedNavHost(navController = navController, startDestination = NavigationItem.Main.route) {
        composable(
            route = NavigationItem.Main.route,
            enterTransition = {
                when (initialState.destination.route) {
                    NavigationItem.DefaultArgs.route ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tween(500)
                        )

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    NavigationItem.DefaultArgs.route ->
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tween(500)
                        )

                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    NavigationItem.DefaultArgs.route ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(500)
                        )

                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    NavigationItem.DefaultArgs.route ->
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(500)
                        )

                    else -> null
                }
            }
        ) {
            val arguments = stringResource(R.string.default_args)
            HomeScreen(composeItems = { composeItems() }) { item ->
                when (item.text) {
                    "Navigation" -> {
                        navController.navigate(
                            NavigationItem.withRouteArgs(
                                navItem = NavigationItem.DefaultArgs,
                                arg = arguments
                            )
                        )
                    }

                    "Jet SavedState" -> {

                    }

                    "Ui" -> {}
                    "Material" -> {}
                }
            }
        }

        composable(
            route = NavigationItem.DefaultArgs.route,
            arguments = listOf(
                navArgument(name = NavigationConstants.Arg_Default) {
                    type = NavType.StringType
                    defaultValue = "Default"
                }
            ),
            enterTransition = {
                when (initialState.destination.route) {
                    NavigationItem.Main.route ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tween(500)
                        )

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    NavigationItem.Main.route ->
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tween(500)
                        )

                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    NavigationItem.Main.route ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(500)
                        )

                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    NavigationItem.Main.route ->
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(500)
                        )

                    else -> null
                }
            }) { backStackEntry ->
            DefaultArgsScreen(
                value = backStackEntry.arguments?.getString(NavigationConstants.Arg_Default),
                handleNavigation = {
                    navController.navigate(
                        NavigationItem.withNullableRouteArgs(
                            NavigationItem.NullableArgs,
                            arg = it
                        )
                    )
                })
        }

        composable(
            route = NavigationItem.NullableArgs.route, arguments = listOf(
                navArgument(name = NavigationConstants.Arg_Nullable) {
                    type = NavType.StringType
                    nullable = true
                })
        ) { backStackEntry ->
            NullableArgsScreen(
                args = backStackEntry.arguments?.getString(NavigationConstants.Arg_Nullable),
                handleCustomSerializableCLick = {
                    val route = withCustomSerializable(
                        NavigationItem.SerializableArgs,
                        ComposeItem("Custom serializable.", R.drawable.ic_close)
                    )
                    navController.navigate(route = route) {
                        popUpTo(NavigationItem.Main.route)
                    }
                })
        }

        composable(
            route = NavigationItem.SerializableArgs.route,
            arguments = listOf(
                navArgument(name = NavigationConstants.Arg_Serializable) {
                    type = NavType.StringType
                    nullable = true
                })
        ) { backStackEntry ->
            SerializableArgsScreen(args = backStackEntry.customSerializable(NavigationConstants.Arg_Serializable)) {
                navController.navigateUp()
            }
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