package com.sr.compose.navigation

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeepLinkVm @Inject constructor() : ViewModel() {
    private var deepLinkProcessed = false

    fun processDeepLinkIfAvailable(intent: Intent?): String? {
        if (!deepLinkProcessed) {
            val intentData = intent?.data?.toString()

            deepLinkProcessed = true
            return intentData
        }
        return null
    }
}