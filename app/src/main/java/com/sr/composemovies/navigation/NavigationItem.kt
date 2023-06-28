package com.sr.composemovies.navigation

import com.sr.composemovies.NavigationConstants
import com.sr.composemovies.R

sealed class NavigationItem(val route: String, val icon: Int, val name: Int) {
    object Main :
        NavigationItem(route = "main", icon = R.drawable.ic_siding, name = R.string.main_desc)

    object Detail :
    /** Navigate with a default parameter */
        NavigationItem(route = "detail/{${NavigationConstants.Arg_Detail}}",
            icon = R.drawable.ic_boat,
            name = R.string.detail_desc)

    companion object {
        private fun all() = listOf(Main, Detail)
        fun findNavItem(route: String?): NavigationItem {
            return all().find { it.route == route } ?: Main
        }

        fun withRouteArgs(navItem: NavigationItem, arg: String): String {
            return "${navItem.route.substringBefore("/")}/$arg"
        }
    }
}