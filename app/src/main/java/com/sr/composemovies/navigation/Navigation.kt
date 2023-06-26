package com.sr.composemovies.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sr.composemovies.ui.screens.DetailScreen
import com.sr.composemovies.ui.screens.MainScreen

//function to navigate with a navController
@Composable
fun Navigation() {
    //navController
    val navController = rememberNavController()
    //navHost with start destination
    NavHost(navController = navController, startDestination = Destination.Main.route) {
        Destination.all().forEach {
            composable(it.route) { entry ->
                when (entry.destination.route) {
                    Destination.Main.route -> {
                        MainScreen(navController = navController)
                    }
                    Destination.Detail.route -> {
                        DetailScreen()
                    }
                    else -> {
                        throw IllegalStateException("Unknown destination in graph.")
                    }
                }
            }
        }
    }
}