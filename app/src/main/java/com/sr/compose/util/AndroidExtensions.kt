package com.sr.compose.util

import android.content.SharedPreferences
import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import com.google.gson.Gson
import com.sr.compose.navigation.NavigationItem


//prefs
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


//navigation
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