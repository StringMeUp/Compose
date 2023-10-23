package com.sr.compose.util.helper

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

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

val bold = SpanStyle(
    color = Color.Black,
    fontSize = 16.sp,
    fontWeight = FontWeight.Bold,
    fontFamily = FontFamily.Monospace
)
val default = SpanStyle(
    color = Color.Black,
    fontWeight = FontWeight.Normal,
    fontFamily = FontFamily.Monospace
)
