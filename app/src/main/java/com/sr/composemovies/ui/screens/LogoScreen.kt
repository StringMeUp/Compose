package com.sr.composemovies.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sr.composemovies.MainViewModel
import com.sr.composemovies.R
import com.sr.composemovies.navigation.NavigationItem

@Composable
@Preview(showBackground = true)
fun LogoScreen(
    navController: NavController? = null,
    value: String? = null,
    viewModel: MainViewModel = viewModel(),
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (title, descr, image, button) = createRefs()

        Text(text = "Logo screen: $value",
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)

            },
            color = Color.Black,
            fontSize = 24.sp,
            textAlign = TextAlign.Center)

        Image(painter = painterResource(id = R.drawable.logo),
            modifier = Modifier
                .size(300.dp)
                .constrainAs(image) {
                    top.linkTo(title.bottom)
                    start.linkTo(title.start)
                    end.linkTo(title.end)

                },
            contentDescription = "Logo")

        Text(text = "Default arguments are passed with the route: " +
                "add/{value} and received in the navGraph i.e. " +
                "backStackEntry.arguments?.getString(“value”). " +
                "Hence it is extremely important to use pre-defined constants.",
            modifier = Modifier
                .constrainAs(descr) {
                    top.linkTo(image.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)

                },
            color = Color.Black,
            fontSize = 18.sp,
            textAlign = TextAlign.Center)

        OutlinedButton(
            modifier = Modifier
                .constrainAs(button) {
                    top.linkTo(descr.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)

                },
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.s_color)),
            onClick = {
                navController?.navigate(NavigationItem.withNullableRouteArgs(NavigationItem.Next,
                    arg = "Pass me all night long!"))
            }) {
            Text(text = "Next", color = Color.White)
        }
    }
}