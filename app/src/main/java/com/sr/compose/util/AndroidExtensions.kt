package com.sr.compose.util

import android.content.SharedPreferences
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
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


/** Navigation */
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
inline fun <reified VM : ViewModel> NavBackStackEntry.parentViewModel(
    navController: NavController,
    route: String,
): VM {
    val parentBackStackEntry = navController.getBackStackEntry(route)
    return hiltViewModel(parentBackStackEntry)
}

/** String*/
fun String.startIndex(string: String): Int{
    return this.indexOf(string = string)
}

fun String.endIndex(string: String): Int{
    return this.indexOf(string = string).plus(string.length)
}