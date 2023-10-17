package com.sr.compose.util

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T?) : Resource<T>()
    data class Error(val responseCode: Int) : Resource<Nothing>()
}