package com.sr.compose.ui.widgets

import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProgressIndicator(){
    CircularProgressIndicator(
        modifier = Modifier.width(48.dp),
        color = MaterialTheme.colorScheme.surface,
        backgroundColor = MaterialTheme.colorScheme.secondary,
    )
}