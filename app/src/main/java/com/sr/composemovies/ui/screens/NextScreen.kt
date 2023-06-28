package com.sr.composemovies.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sr.composemovies.R

@Composable
fun NextScreen(navController: NavController, args: String?) {
    SetUpView(args = args) {
        navController.navigateUp()
    }
}

@Preview(showBackground = true)
@Composable
fun SetUpView(args: String? = null, onClick: () -> Unit = {}) {
    Text(text = args ?: "Jebaji ga",
        textAlign = TextAlign.Center,
        color = Color.Black,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 56.dp),
        fontSize = 24.sp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 32.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedButton(
            enabled = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.s_color)),
            onClick = { onClick() }) {
            Text(text = "Done", color = Color.White)
        }
    }
}

