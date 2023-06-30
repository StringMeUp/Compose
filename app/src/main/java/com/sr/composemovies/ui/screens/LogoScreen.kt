package com.sr.composemovies.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
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

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://cdn.pixabay.com/photo/2023/04/02/23/08/ai-generated-7895583_1280.jpg")
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(24.dp)
                .constrainAs(image) {
                    top.linkTo(title.bottom)
                    start.linkTo(title.start)
                    end.linkTo(title.end)

                }
                .clip(shape = RoundedCornerShape(16.dp)), contentDescription = "At sea")

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