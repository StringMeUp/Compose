package com.sr.compose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.sr.compose.R
import com.sr.compose.navigation.ComposeItem

@Composable
fun SerializableArgsScreen(args: ComposeItem?, onButtonClick: () -> Unit = {}) {
    SetUpSerializable(args = args) {
        onButtonClick()
    }
}

@Preview(showBackground = true)
@Composable
fun SetUpSerializable(args: ComposeItem? = null, onClick: () -> Unit = {}) {
    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {
        val (title, descr, spacer, image, button) = createRefs()
        Text(text = args?.text ?: "Well, this didn't work honey.",
            textAlign = TextAlign.Center,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
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

        Text(text = stringResource(id = R.string.custom_serial),
            textAlign = TextAlign.Center,
            color = Color.Black,
            modifier = Modifier
                .constrainAs(descr) {
                    top.linkTo(spacer.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .padding(16.dp),
            fontSize = 18.sp)

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

        OutlinedButton(
            enabled = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .constrainAs(button) {
                    top.linkTo(image.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = com.sr.compose.R.color.s_color)),
            onClick = { onClick() }) {
            Text(text = "Done", color = Color.White)
        }
    }
}