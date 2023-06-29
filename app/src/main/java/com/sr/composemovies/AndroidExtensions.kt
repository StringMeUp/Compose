package com.sr.composemovies

import android.content.SharedPreferences
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.google.gson.Gson
import com.sr.composemovies.navigation.NavigationItem


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

inline fun <reified T> NavController.withCustomSerializable(navItem: NavigationItem, arg: T): String {
    val gson: Gson = Gson()
    val jsonString = gson.toJson(arg)
    return "${navItem.route.substringBefore("=")}=$jsonString"
}