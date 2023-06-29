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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.sr.composemovies.MainViewModel
import com.sr.composemovies.R

@Composable
fun SerializableScreen(navController: NavController, args: MainViewModel.ComposeItem?) {
    SetUpSerializable(args = args) {
        navController.navigateUp()
    }
}

@Preview(showBackground = true)
@Composable
fun SetUpSerializable(args: MainViewModel.ComposeItem? = null, onClick: () -> Unit = {}) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (title, descr) = createRefs()
        Text(text = args?.text ?: "Well, this didn't work honey.",
            textAlign = TextAlign.Center,
            color = Color.Black,
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .padding(top = 56.dp),
            fontSize = 24.sp)

        Text(text = "In this case we used Gson to be able to simply " +
                "serialize/deserialize our custom object and pass it along. " +
                "In comparison to safe-args/or the out-of-the-box arguments " +
                "in the fragment navController component (graph) this is just ridiculous.",
            textAlign = TextAlign.Center,
            color = Color.Black,
            modifier = Modifier
                .constrainAs(descr) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .padding(36.dp),
            fontSize = 24.sp)
    }

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

