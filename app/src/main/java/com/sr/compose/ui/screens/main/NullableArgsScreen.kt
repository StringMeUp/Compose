package com.sr.compose.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.sr.compose.R
import com.sr.compose.R.color
import com.sr.compose.R.drawable
import com.sr.compose.ui.widgets.bold
import com.sr.compose.ui.widgets.default


@Composable
fun NullableArgsScreen(args: String?, onClick: () -> Unit = {}) {
    SetUpView(args = args) {
        onClick()
    }
}

@Preview(showBackground = true)
@Composable
fun SetUpView(args: String? = null, onClick: () -> Unit = {}) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        val (argText, descrText, card, button) = createRefs()

        Text(buildAnnotatedString {
            withStyle(bold.copy(fontSize = 26.sp)) {
                append(args ?: "Hmm...this did not work as expected!")
            }
        },
            textAlign = TextAlign.Center,
            color = Color.Black,
            modifier = Modifier
                .padding(top = 24.dp)
                .constrainAs(argText) {
                    centerHorizontallyTo(parent)
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
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = drawable.all_night),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }

        Text(buildAnnotatedString {
            val text = stringResource(id = R.string.nullable_args_text)
            withStyle(default.copy(fontSize = 18.sp)) {
                append(text)
            }
        }, modifier = Modifier.constrainAs(descrText) {
            top.linkTo(card.bottom)
        }, color = Color.Black,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )

        OutlinedButton(enabled = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .constrainAs(button) {
                    top.linkTo(descrText.bottom)
                    centerHorizontallyTo(parent)
                },
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = color.s_color)),
            onClick = { onClick() }) {
            Text(text = "Navigate with serializable?!", color = Color.White)
        }
    }
}

