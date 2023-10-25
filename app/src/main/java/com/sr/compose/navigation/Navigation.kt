package com.sr.compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost


@Composable
fun AppNavigation(
    navController: NavHostController,
    composeItems: () -> List<ComposeItem> = { ComposeItem.generate() },
) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Main.route,
        route = Graph.ROOT
    ) {
        rootGraph(navController, composeItems)
        bottomGraph(navController)
    }
}