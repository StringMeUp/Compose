package com.sr.compose.ui.screens.bottomnavscreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview(showBackground = true)
fun BottomHome() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Home",
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
            style = typography.h4,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun BottomProfile() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Profile",
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
            style = typography.h4,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun BottomContacts() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Contacts",
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
            style = typography.h4,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun BottomSettings() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Settings",
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
            style = typography.h4,
        )
    }
}