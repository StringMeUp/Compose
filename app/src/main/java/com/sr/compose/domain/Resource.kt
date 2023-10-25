package com.sr.compose.domain

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T?, var authUrl: () -> String? = { null }) : Resource<T>()
    data class Error(val responseCode: Int) : Resource<Nothing>()
}