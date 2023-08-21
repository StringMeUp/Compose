package com.sr.compose.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.sr.compose.NavigationConstants
import com.sr.compose.R
import com.sr.compose.customSerializable
import com.sr.compose.ui.screens.*
import com.sr.compose.ui.screens.bottomnavscreens.BottomHome
import com.sr.compose.ui.screens.bottomnavscreens.BottomTwo
import com.sr.compose.withCustomSerializable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation(
    navController: NavHostController,
    composeItems: () -> List<ComposeItem> = { ComposeItem.generate() },
) {
    //navHost with start destination
    AnimatedNavHost(
        navController = navController,
        startDestination = NavigationItem.Main.route,
        route = Graph.ROOT
    ) {
        rootArgsGraph(navController, composeItems)
        bottomNavGraph()
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.bottomNavGraph() {
    navigation(
        startDestination = NavigationItem.BottomMain.BottomNavHome.route,
        route = Graph.BOTTOM
    ) {
        composable(route = NavigationItem.BottomMain.BottomNavHome.route) {
            BottomHome()
        }
        composable(route = NavigationItem.BottomMain.BottomNavProfile.route) {
            BottomTwo()
        }
        composable(route = NavigationItem.BottomMain.BottomNavContacts.route) {
            BottomTwo()
        }
        composable(route = NavigationItem.BottomMain.BottomNavSettings.route) {
            BottomTwo()
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.rootArgsGraph(
    navController: NavController,
    composeItems: () -> List<ComposeItem> = { emptyList() },
) {
    composable(
        route = NavigationItem.Main.route,
        enterTransition = {
            when (initialState.destination.route) {
                NavigationItem.DefaultArgs.route -> enterSlideInFromLeft()
                else -> null
            }
        },
        exitTransition = {
            when (targetState.destination.route) {
                NavigationItem.DefaultArgs.route -> exitSlideOutToLeft()
                else -> null
            }
        },
        popEnterTransition = {
            when (initialState.destination.route) {
                NavigationItem.DefaultArgs.route -> popEnterSlideInFromRight()
                else -> null
            }
        },
        popExitTransition = {
            when (targetState.destination.route) {
                NavigationItem.DefaultArgs.route -> popExitSlideOutToRight()
                else -> null
            }
        }
    ) {
        val arguments = stringResource(R.string.default_args)
        HomeScreen(composeItems = { composeItems() }) { item ->
            when (item.text) {
                "Arguments" -> {
                    navController.navigate(
                        NavigationItem.withRouteArgs(
                            navItem = NavigationItem.DefaultArgs,
                            arg = arguments
                        )
                    )
                }

                "Bottom Navigation" -> {
                    navController.navigate(Graph.BOTTOM)
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
                NavigationItem.Main.route -> enterSlideInFromLeft()
                else -> null
            }
        },
        exitTransition = {
            when (targetState.destination.route) {
                NavigationItem.Main.route -> exitSlideOutToLeft()
                else -> null
            }
        },
        popEnterTransition = {
            when (initialState.destination.route) {
                NavigationItem.Main.route -> popEnterSlideInFromRight()
                else -> null
            }
        },
        popExitTransition = {
            when (targetState.destination.route) {
                NavigationItem.Main.route -> popExitSlideOutToRight()
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
}