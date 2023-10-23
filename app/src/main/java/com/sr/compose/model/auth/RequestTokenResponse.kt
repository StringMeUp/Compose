package com.sr.compose.model.auth

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class RequestTokenResponse(
    val success: Boolean,
    @SerializedName("expires_at")
    val expiresAt: LocalDate,
    @SerializedName("request_token")
    val requestToken: String,
)