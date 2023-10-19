package com.sr.compose.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImagesResponse(val backdrops: List<MovieImage>) : Parcelable {
    @Parcelize
    data class MovieImage(
        @SerializedName("aspect_ratio")
        val aspectRatio: Double,
        val height: Int,
        @SerializedName("file_path")
        val filePath: String,
        val width: Int,
    ) : Parcelable
}
