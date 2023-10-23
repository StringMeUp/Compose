package com.sr.compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.sr.compose.ui.screens.bottomnavscreens.ContactsScreen
import com.sr.compose.ui.screens.bottomnavscreens.SettingsScreen
import com.sr.compose.ui.screens.bottomnavscreens.detail.MovieDetailScreen
import com.sr.compose.ui.screens.bottomnavscreens.movie.MovieScreen
import com.sr.compose.ui.screens.bottomnavscreens.movie.MovieViewModel
import com.sr.compose.ui.screens.bottomnavscreens.profile.ProfileScreen
import com.sr.compose.util.parentViewModel


@Composable
fun AppNavigation(
    navController: NavHostController,
    composeItems: () -> List<ComposeItem> = { ComposeItem.generate() },
    startDestination: String? = NavigationItem.BottomNavMain.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination!!,
        route = Graph.BOTTOM
    ) {
//        rootGraph(navController, composeItems)
//        bottomGraph(navController)

        composable(route = NavigationItem.BottomNavMain.route) {
            MovieScreen(navigateToDetails = { movieId ->
                navController.navigate(
                    route = NavigationItem.withRouteArgs(
                        NavigationItem.BottomNavMain.MovieDetail,
                        movieId
                    )
                )

                /**  Using replace  navController.navigate(
                route = NavigationItem.BottomNavigation.MovieDetail.route.replace(
                oldValue = NavigationConstants.Arg_Movie_Detail,
                newValue = movieId
                ))*/

                /**  Using replace  navController.navigate(
                route = NavigationItem.BottomNavigation.MovieDetail.route.replace(
                oldValue = NavigationConstants.Arg_Movie_Detail,
                newValue = movieId
                ))*/

            })
        }
        composable(
            route = NavigationItem.BottomNavMain.BottomNavProfile.route,
            deepLinks = listOf(navDeepLink {
                uriPattern = NavigationConstants.Profile_Uri_Pattern
            })
        ) {
            val rt = it.arguments?.getString(NavigationConstants.Request_Token)
            ProfileScreen(request_token = rt) { navController.popBackStack() }
        }
        composable(route = NavigationItem.BottomNavMain.BottomNavContacts.route) {
            ContactsScreen()
        }
        composable(route = NavigationItem.BottomNavMain.BottomNavSettings.route) {
            SettingsScreen()
        }
        composable(
            route = NavigationItem.BottomNavMain.MovieDetail.route,
            arguments = listOf(navArgument(name = NavigationConstants.Arg_Movie_Detail) {
                type = NavType.IntType
                nullable = false

            })
        ) {
            val arguments = it.arguments?.getInt(NavigationConstants.Arg_Movie_Detail)
            val viewModel: MovieViewModel = parentViewModel(
                navController = navController,
                route = NavigationItem.BottomNavMain.route
            )
            MovieDetailScreen(args = arguments!!, viewModel = viewModel)
        }
    }
}