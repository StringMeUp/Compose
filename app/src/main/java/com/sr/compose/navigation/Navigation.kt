package com.sr.compose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.sr.compose.MainViewModel
import com.sr.compose.R
import com.sr.compose.ui.screens.bottomnavscreens.ContactsScreen
import com.sr.compose.ui.screens.bottomnavscreens.MovieScreen
import com.sr.compose.ui.screens.bottomnavscreens.ProfileScreen
import com.sr.compose.ui.screens.bottomnavscreens.SettingsScreen
import com.sr.compose.ui.screens.bottomnavscreens.detail.MovieDetailScreen
//import com.sr.compose.ui.screens.bottomnavscreens.detail.MovieDetailScreen
import com.sr.compose.ui.screens.main.DefaultArgsScreen
import com.sr.compose.ui.screens.main.HomeScreen
import com.sr.compose.ui.screens.main.NullableArgsScreen
import com.sr.compose.ui.screens.main.SerializableArgsScreen
import com.sr.compose.util.NavigationConstants
import com.sr.compose.util.customSerializable
import com.sr.compose.util.withCustomSerializable


@Composable
fun AppNavigation(
    navController: NavHostController,
    composeItems: () -> List<ComposeItem> = { ComposeItem.generate() },
    sharedVm: MainViewModel
) {
    //navHost with start destination
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Main.route,
        route = Graph.ROOT
    ) {
        rootArgsGraph(navController, composeItems)
        bottomNavGraph(navController, sharedVm)
    }
}

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
        }
    ) { backStackEntry ->
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

fun NavGraphBuilder.bottomNavGraph(navController: NavHostController, sharedVm: MainViewModel) {
    navigation(
        startDestination = NavigationItem.BottomNavigation.BottomNavMovie.route,
        route = Graph.BOTTOM
    ) {
        composable(route = NavigationItem.BottomNavigation.BottomNavMovie.route) {
            MovieScreen(navigateToDetails = { movieId ->
                navController.navigate(
                    route = NavigationItem.withRouteArgs(
                        NavigationItem.BottomNavigation.MovieDetail,
                        movieId
                    )
                )

                /**  Using replace  navController.navigate(
                route = NavigationItem.BottomNavigation.MovieDetail.route.replace(
                oldValue = NavigationConstants.Arg_Movie_Detail,
                newValue = movieId
                ))*/
            })
        }
        composable(route = NavigationItem.BottomNavigation.BottomNavProfile.route) {
            ProfileScreen()
        }
        composable(route = NavigationItem.BottomNavigation.BottomNavContacts.route) {
            ContactsScreen()
        }
        composable(route = NavigationItem.BottomNavigation.BottomNavSettings.route) {
            SettingsScreen()
        }
        composable(
            route = NavigationItem.BottomNavigation.MovieDetail.route,
            arguments = listOf(navArgument(name = NavigationConstants.Arg_Movie_Detail) {
                type = NavType.StringType
                defaultValue = ""
                nullable = false

            })
        ) {
            val arguments = it.arguments?.getString(NavigationConstants.Arg_Movie_Detail)
            MovieDetailScreen(args = arguments!!, sharedVm)
        }
    }
}