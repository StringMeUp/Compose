package com.sr.compose.ui.widgets

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sr.compose.R

@Composable
fun AppButton(
    text: String = "",
    isEnabled: Boolean = true,
    onClickEvent: () -> Unit = {},
    modifier: Modifier,
) {
    OutlinedButton(
        onClick = { onClickEvent() },
        modifier = modifier,
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(id = R.color.s_color),
            disabledBackgroundColor = Color.Gray,
            contentColor = colorResource(id = R.color.white)

        )
    ) {
        Text(text = text)
    }
}