package com.sr.compose.ui.screens.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sr.compose.R
import com.sr.compose.R.color
import com.sr.compose.util.endIndex
import com.sr.compose.util.helper.bold
import com.sr.compose.util.helper.default
import com.sr.compose.util.startIndex

@Composable
@Preview(showBackground = true)
fun DefaultArgsScreen(
    value: String = "",
    onClick: (arg: String?) -> Unit = {},
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
    ) {
        val (title, descr, image, button) = createRefs()

        Text(
            buildAnnotatedString {
                withStyle(bold.copy(fontSize = 26.sp)) {
                    append("Default Arguments: $value")
                }
            },
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 12.dp)
                .constrainAs(title) {
                    centerHorizontallyTo(parent)
                }
        )

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

        Text(
            buildAnnotatedString {
                val text = stringResource(id = R.string.default_args_text)
                withStyle(default.copy(fontSize = 18.sp)) {
                    append(text)
                    addStyle(bold.copy(fontSize = 18.sp), 0, "Default arguments".length)
                    addStyle(
                        bold.copy(fontSize = 18.sp),
                        text.startIndex("https://www.example.com/detail{args}"),
                        text.endIndex("https://www.example.com/detail{args}"),
                    )

                    addStyle(
                        bold.copy(fontSize = 18.sp),
                        text.startIndex("backStackEntry.arguments?.getString(“value”)"),
                        text.endIndex("backStackEntry.arguments?.getString(“value”)"),
                    )
                }
            },
            modifier = Modifier
                .constrainAs(descr) {
                    top.linkTo(image.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            color = Color.Black,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )

        OutlinedButton(
            modifier = Modifier
                .constrainAs(button) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)

                }
                .padding(24.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = color.s_color)),
            onClick = {
                onClick("Hooray this was a success. You just received nullable args.")
//                handleNavigation(null)
            }) {
            Text(text = "Try nullable arguments?", color = Color.White)
        }
    }
}