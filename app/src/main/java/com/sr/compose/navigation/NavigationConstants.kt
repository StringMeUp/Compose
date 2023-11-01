package com.sr.compose.navigation

object NavigationConstants {
    const val Arg_Default = "arg_default_value"
    const val Arg_Nullable = "arg_nullable_value"
    const val Arg_Serializable = "arg_serializable_value"
    const val Arg_Movie_Detail = "arg_movie_detail"

    const val Request_Token = "rt_arg"
    const val Scheme = "srcapp"
    const val Profile_Uri_Pattern = "$Scheme://com.sr.compose/{${Request_Token}}"
}