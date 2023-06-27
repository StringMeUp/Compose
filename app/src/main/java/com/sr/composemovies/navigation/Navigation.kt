package com.sr.composemovies.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.sr.composemovies.MainViewModel
import com.sr.composemovies.ui.screens.DetailScreen
import com.sr.composemovies.ui.screens.MainScreen

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
                            animationSpec = tween(2000))
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    NavigationItem.Detail.route ->
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tween(700))
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    NavigationItem.Detail.route ->
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(700))
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    NavigationItem.Detail.route ->
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(700))
                    else -> null
                }
            }
        ) {
            MainScreen(navController = navController)
        }

        composable(route = NavigationItem.Detail.route,
            enterTransition = {
                when (initialState.destination.route) {
                    NavigationItem.Main.route ->
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tween(700))
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    NavigationItem.Main.route ->
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tween(700))
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    NavigationItem.Main.route ->
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(700))
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    NavigationItem.Main.route ->
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(700))
                    else -> null
                }
            }) {
            DetailScreen()
        }

//        composable(route = NavigationItem.Main.route) {
//            MainScreen(navController = navController)
//        }
//        composable(route = NavigationItem.Detail.route) {
//            DetailScreen()
//        }
    }
}