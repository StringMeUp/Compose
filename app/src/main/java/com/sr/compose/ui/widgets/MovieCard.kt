package com.sr.compose.ui.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.Lifecycle
import coil.compose.AsyncImage
import com.sr.compose.model.Movie
import com.sr.compose.ui.helper.StyledText


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieCard(movie: Movie, navigateToDetails: (movieId: String) -> Unit = {}) {
    val isVisible = rememberSaveable { mutableStateOf(false) }

    val bold = SpanStyle(
        color = Color.Black,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace
    )
    val default = SpanStyle(
        color = Color.Gray,
        fontFamily = FontFamily.Monospace
    )

    val lifecycleOwner = LocalLifecycleOwner.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(5.dp)
            .clip(shape = RoundedCornerShape(8.dp)),
        onClick = {
            if (lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                navigateToDetails(movie.id)
            }
        }
    ) {

        ConstraintLayout(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
        ) {
            val (poster, titleText, infoText, toggle, detailText) = createRefs()

            AsyncImage(
                model = movie.images.first(),
                contentDescription = "Poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .constrainAs(poster) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
                    .size(100.dp)
                    .padding(6.dp)
                    .clip(CircleShape)
                    .wrapContentSize()
            )

            StyledText(
                text1 = "Title",
                text2 = movie.title,
                bold = bold,
                default = default,
                modifier = Modifier
                    .padding(start = 6.dp, top = 12.dp, end = 6.dp)
                    .constrainAs(titleText) {
                        start.linkTo(poster.end)
                        end.linkTo(toggle.start)
                        width = Dimension.fillToConstraints
                    }
            )

            Text(
                buildAnnotatedString {
                    withStyle(bold) { append("Genre:") }
                    withStyle(default) { append("${movie.genre}\n") }
                    withStyle(bold) { append("Director:") }
                    withStyle(default) {  append(movie.director) }
                }, modifier = Modifier
                    .wrapContentHeight()
                    .constrainAs(infoText) {
                        top.linkTo(titleText.bottom)
                        start.linkTo(titleText.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .padding(start = 6.dp, bottom = 6.dp, end = 6.dp)
            )

            Icon(
                imageVector = if (isVisible.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = "Down Arrow",
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(25.dp)
                    .clickable {
                        isVisible.value = !isVisible.value
                    }
                    .constrainAs(toggle) {
                        end.linkTo(parent.end)
                    },
                tint = Color.DarkGray
            )

            AnimatedVisibility(visible = isVisible.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 4.dp, end = 4.dp, bottom = 4.dp)
                    .constrainAs(detailText) {
                        top.linkTo(infoText.bottom)
                    }
            ) {
                Text(
                    buildAnnotatedString {
                        withStyle(bold) { append("Plot:") }
                        withStyle(default) { append("${movie.plot}\n\n") }
                        withStyle(bold) { append("Actors:") }
                        withStyle(default) { append("${movie.actors}\n\n") }
                        withStyle(bold) { append("Year:") }
                        withStyle(default) { append("${movie.year}\n") }
                        withStyle(bold) { append("Rating:") }
                        withStyle(default) { append(movie.rating) }
                    }, modifier = Modifier
                        .wrapContentHeight()
                        .padding(10.dp)
                )
            }
        }
    }
}
