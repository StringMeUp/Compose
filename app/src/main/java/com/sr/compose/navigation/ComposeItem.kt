package com.sr.compose.navigation

import android.os.Parcelable
import com.sr.compose.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class ComposeItem(
    val text: String,
    val image: Int,
    val type: ComposeItemType,
) : Parcelable {
    companion object {
        fun generate() = listOf(
            ComposeItem("Arguments", R.drawable.logo, ComposeItemType.ARGS),
            ComposeItem("Bottom Navigation with TMDB", R.drawable.jet, ComposeItemType.BOTTOM),
            ComposeItem("Drawer", R.drawable.ui, ComposeItemType.DRAWER),
            ComposeItem("BottomSheet", R.drawable.material, ComposeItemType.SHEET)
        )
    }
}