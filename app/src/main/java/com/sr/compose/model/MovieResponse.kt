package com.sr.compose.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

@Parcelize
data class MovieResponse(
    val page: Int,
    @SerializedName("results")
    val movies: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int,
) : Parcelable {
    @Parcelize
    data class Movie(
        val adult: Boolean,
        @SerializedName("backdrop_path")
        val backdropPath: String,
        @SerializedName("genre_ids")
        val genreIds: List<Int>,
        val id: Int,
        @SerializedName("original_language")
        val originalLanguage: String,
        @SerializedName("original_title")
        val originalTitle: String,
        val overview: String,
        val popularity: Float,
        @SerializedName("poster_path")
        val posterPath: String,
        @SerializedName("release_date")
        val releaseDate: LocalDate,
        val title: String,
        @SerializedName("vote_average")
        val voteAverage: Double,
        @SerializedName("vote_count")
        val voteCount: Int,
    ) : Parcelable
}
