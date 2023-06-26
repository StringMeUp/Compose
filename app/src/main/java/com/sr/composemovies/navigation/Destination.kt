package com.sr.composemovies.navigation

sealed class Destination(val route: String) {
    object Main : Destination("main")
    object Detail : Destination("detail")

    companion object {
        fun all(): List<Destination>{
            return listOf(Main, Detail)
        }

        fun findDestination(route: String?): Destination {
            return when (route) {
                Main.route -> Main
                Detail.route -> Detail
                else -> throw IllegalArgumentException("Null value for destintion.")
            }
        }
    }
}


