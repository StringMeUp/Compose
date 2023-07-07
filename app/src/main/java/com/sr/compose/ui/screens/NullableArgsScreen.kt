package com.sr.compose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.sr.compose.MainViewModel
import com.sr.compose.R.*
import com.sr.compose.navigation.NavigationItem
import com.sr.compose.withCustomSerializable


@Composable
fun NullableArgsScreen(navController: NavController, args: String?) {
    SetUpView(args = args) {

        val route = withCustomSerializable(NavigationItem.SerializableArgs,
            MainViewModel.ComposeItem("Custom serializable.", drawable.ic_close))
        navController.navigate(route = route) {
            popUpTo(NavigationItem.Main.route)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SetUpView(args: String? = null, onClick: () -> Unit = {}) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (argText, card) = createRefs()
        Text(text = args ?: "Hmmm...this did not work as expected!",
            textAlign = TextAlign.Center,
            color = Color.Black,
            modifier = Modifier
                .constrainAs(argText) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold)

        Card(shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .constrainAs(card) {
                    top.linkTo(argText.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
            Image(modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = drawable.all_night),
                contentDescription = "",
                contentScale = ContentScale.Crop)
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 32.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedButton(enabled = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = color.s_color)),
            onClick = { onClick() }) {
            Text(text = "Navigate with serializable?!", color = Color.White)
        }
    }
}

