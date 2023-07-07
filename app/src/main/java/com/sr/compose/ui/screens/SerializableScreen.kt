package com.sr.compose.ui.screens

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.sr.compose.R
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.sr.compose.MainViewModel

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
        val (title, descr, spacer, image) = createRefs()
        Text(text = args?.text ?: "Well, this didn't work honey.",
            textAlign = TextAlign.Center,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier
            .height(24.dp)
            .fillMaxWidth()
            .constrainAs(spacer) {
                top.linkTo(title.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

        Text(text = "Passing custom Serializables/Parcelables is strictly speaking against core android principles, " +
                "and also not recommended by the Jetpack Compose team. However, it can be done.",
            textAlign = TextAlign.Center,
            color = Color.Black,
            modifier = Modifier
                .constrainAs(descr) {
                    top.linkTo(spacer.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(),
            fontSize = 24.sp)

        Image(
            painter = painterResource(id = R.drawable.mrbunny),
            contentDescription = "Custom bunny serializable,",
            modifier = Modifier
                .padding(24.dp)
                .clip(RoundedCornerShape(corner = CornerSize(8.dp)))
                .size(200.dp)
                .constrainAs(image) {
                    top.linkTo(descr.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            contentScale = ContentScale.Crop)
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
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = com.sr.compose.R.color.s_color)),
            onClick = { onClick() }) {
            Text(text = "Done", color = Color.White)
        }
    }
}

