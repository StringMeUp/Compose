package com.sr.compose.util

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.sr.compose.navigation.NavigationItem


/** Prefs */
inline fun <reified T> SharedPreferences.getSerializable(key: String): T? {
    val gson: Gson = Gson()
    return getString(key, null)?.let {
        gson.fromJson(it, T::class.java)
    }
}

inline fun <reified T> SharedPreferences.putSerializable(key: String, value: T) {
    val gson: Gson = Gson()
    val jsonString = gson.toJson(value)
    edit().putString(key, jsonString).commit()
}

/** Arguments */
inline fun <reified T> NavBackStackEntry.customSerializable(key: String): T? {
    val gson: Gson = Gson()
    return arguments?.getString(key, null)?.let {
        gson.fromJson(it, T::class.java)
    }
}

inline fun <reified T> withCustomSerializable(navItem: NavigationItem, arg: T): String {
    val gson: Gson = Gson()
    val jsonString = gson.toJson(arg)
    return "${navItem.route.substringBefore("=")}=$jsonString"
}

/** OnClick */
inline fun Modifier.clickWithDebounce(
    debounceInterval: Long = 700,
    crossinline onClick: () -> Unit,
): Modifier {
    var lastClickTime = 0L
    val currentTime = System.currentTimeMillis()
    return clickable {
        if ((currentTime - lastClickTime) < debounceInterval) return@clickable
        lastClickTime = currentTime
        onClick()
    }
}

/** ViewModel */
@Composable
inline fun <reified VM : ViewModel> parentViewModel(
    navController: NavController,
    route: String,
): VM {
    val parentBackStackEntry = navController.getBackStackEntry(route)
    return hiltViewModel(parentBackStackEntry)
}

/** String */
fun String.startIndex(string: String): Int {
    return this.indexOf(string = string)
}

fun String.endIndex(string: String): Int {
    return this.indexOf(string = string).plus(string.length)
}

/** Intent */
fun Intent.launch(context: Context) {
    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (context.packageManager.queryIntentActivities(
                this, PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_DEFAULT_ONLY.toLong())
            ).isNotEmpty()
        ) {
            context.startActivity(this)
        }
    } else {
        if (context.packageManager.queryIntentActivities(
                this, PackageManager.GET_META_DATA
            ).isNotEmpty()
        ) {
            context.startActivity(this)
        }
    }
}

fun NavHostController.navigateBottomNavigationScreen(screen: NavigationItem) = navigate(screen.route) {
    val navigationRoutes = NavigationItem.BottomNavMain.bottomNavDestinations().map { it.route }
    val firstBottomBarDestination = currentBackStack.value
        .firstOrNull { navigationRoutes.contains(it.destination.route) }
        ?.destination
    if (firstBottomBarDestination != null) {
        popUpTo(firstBottomBarDestination.id) {
            inclusive = true
            saveState = true
        }
    }
    launchSingleTop = true
    restoreState = true
}