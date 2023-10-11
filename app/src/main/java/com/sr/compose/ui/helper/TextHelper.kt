package com.sr.compose.ui.helper

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@Composable
fun StyledText(
    text1: String,
    bold: SpanStyle,
    text2: String,
    default: SpanStyle,
    modifier: Modifier,
) {
    Text(buildAnnotatedString {
        withStyle(style = bold) { append("$text1:") }
        withStyle(style = default) { append(text2) }
    }, modifier = modifier)
}
