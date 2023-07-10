package com.sr.compose.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class UiUserModel(val name: String, val surname: String) : Parcelable