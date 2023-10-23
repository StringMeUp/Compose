package com.sr.compose.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.sr.compose.ui.screens.bottomnavscreens.ContactsScreen
import com.sr.compose.ui.screens.bottomnavscreens.profile.ProfileScreen
import com.sr.compose.ui.screens.bottomnavscreens.SettingsScreen
import com.sr.compose.ui.screens.bottomnavscreens.detail.MovieDetailScreen
import com.sr.compose.ui.screens.bottomnavscreens.movie.MovieScreen
import com.sr.compose.ui.screens.bottomnavscreens.movie.MovieViewModel
import com.sr.compose.util.parentViewModel


fun NavGraphBuilder.bottomNavGraph(navController: NavHostController) {
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
                type = NavType.IntType
                nullable = false

            })
        ) {
            val arguments = it.arguments?.getInt(NavigationConstants.Arg_Movie_Detail)
            val viewModel: MovieViewModel = parentViewModel(
                navController = navController,
                route = NavigationItem.BottomNavigation.BottomNavMovie.route
            )
            MovieDetailScreen(args = arguments!!, viewModel = viewModel)
        }
    }
}