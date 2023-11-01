package com.sr.compose.navigation

import android.content.Intent
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeepLinkViewModel @Inject constructor() : ViewModel() {
    private var isProcessed = false
    fun processDeepLink(intent: Intent?): String? {
        if (!isProcessed) {
            val intentData = intent?.data?.toString()
            isProcessed = true
            return intentData
        }
        return null
    }
}