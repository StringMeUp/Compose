package com.sr.compose.navigation

import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sr.compose.R
import com.sr.compose.ui.screens.main.DefaultArgsScreen
import com.sr.compose.ui.screens.main.HomeScreen
import com.sr.compose.ui.screens.main.NullableArgsScreen
import com.sr.compose.ui.screens.main.SerializableArgsScreen
import com.sr.compose.util.customSerializable
import com.sr.compose.util.enterSlideInFromLeft
import com.sr.compose.util.exitSlideOutToLeft
import com.sr.compose.util.popEnterSlideInFromRight
import com.sr.compose.util.popExitSlideOutToRight
import com.sr.compose.util.withCustomSerializable

fun NavGraphBuilder.rootGraph(
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
        val allItems = ComposeItem.generate()
        HomeScreen(composeItems = { composeItems() }) { item ->
            allItems.find { it.text == item.text }.let {
                when (it?.type) {
                    ComposeItemType.ARGS -> {
                        navController.navigate(
                            NavigationItem.withRouteArgs(
                                navItem = NavigationItem.DefaultArgs,
                                arg = arguments
                            )
                        )
                    }

                    ComposeItemType.BOTTOM -> {
                        navController.navigate(Graph.BOTTOM)
                    }

                    ComposeItemType.UI -> {}
                    ComposeItemType.MATERIAL -> {}
                    else -> {}
                }
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
            value = backStackEntry.arguments?.getString(NavigationConstants.Arg_Default) ?: "",
            onClick = {
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
            onClick = {
                val route = withCustomSerializable(
                    NavigationItem.SerializableArgs,
                    ComposeItem(
                        "...thumbs up for custom Serializable objects...",
                        R.drawable.ic_close,
                        ComposeItemType.ARGS
                    )
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