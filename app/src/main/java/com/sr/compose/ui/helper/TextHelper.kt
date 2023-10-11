package com.sr.compose.ui.helper

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
fun StyledText(text1: String, color1: Color, text2: String, color2: Color, modifier: Modifier) {
    Text(buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = color1,
                fontSize = 16.sp,
                fontFamily = FontFamily.Monospace
            )
        ) {
            append("$text1:")
        }
        withStyle(style = SpanStyle(color = color2, fontFamily = FontFamily.Monospace)) {
            append(text2)
        }
    }, modifier = modifier)
}
