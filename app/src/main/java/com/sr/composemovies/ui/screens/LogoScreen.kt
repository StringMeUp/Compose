package com.sr.composemovies.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sr.composemovies.MainViewModel
import com.sr.composemovies.R

@Composable
@Preview(showBackground = true)
fun LogoScreen(
    navController: NavController? = null,
    value: String? = null,
    viewModel: MainViewModel = viewModel(),
) {

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Logo screen: $value",
            modifier = Modifier.padding(top = 24.dp),
            color = Color.Black,
            fontSize = 24.sp,
            textAlign = TextAlign.Center)

        Image(painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(300.dp))

        Text(text = "Default arguments are passed with the route: " +
                "add/{value} and received in the navGraph i.e. " +
                "backStackEntry.arguments?.getString(“value”). " +
                "Hence it is extremely important to use pre-defined constants.",
            color = Color.Black,
            modifier = Modifier.padding(12.dp),
            fontSize = 18.sp,
            textAlign = TextAlign.Center)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally) {
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.s_color)),
                onClick = { /*TODO navigate further down the road*/ }) {
                Text(text = "Next", color = Color.White)
            }
        }
    }
}