package com.sr.compose.ui.widgets

import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppTextField(
    label: String = "",
    text: String = "",
    onValueChange: (value: String) -> Unit,
    modifier: Modifier,
) {
    OutlinedTextField(
        value = text,
        onValueChange = { onValueChange(it) },
        label = { Text(label) },
        modifier = modifier
    )
}