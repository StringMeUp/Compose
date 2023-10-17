package com.sr.compose.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenresResponse(
    val genres: List<Genre>,
) : Parcelable {
    @Parcelize
    data class Genre(
        val id: Int,
        val name: String,
    ) : Parcelable
}
