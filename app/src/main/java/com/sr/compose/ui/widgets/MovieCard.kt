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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.Lifecycle
import coil.compose.AsyncImage

import com.sr.compose.model.MovieResponse
import com.sr.compose.ui.helper.StyledText
import com.sr.compose.ui.helper.bold
import com.sr.compose.ui.helper.default


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieCard(
    movie: MovieResponse.Movie? = null,
    navigateToDetails: (movieId: Int) -> Unit = {},
    imagePath: () -> String = { "" },
    genres: () -> String = { "" },
    toggle: () -> Unit = {},
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(5.dp)
            .clip(shape = RoundedCornerShape(8.dp)),
        onClick = {
            if (lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                movie?.let { navigateToDetails(it.id) }
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
                model = imagePath(),
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
                text2 = movie?.title ?: "",
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
                    withStyle(default) { append("${genres()}\n") }
                    withStyle(bold) { append("Vote average:") }
                    withStyle(default) { append("${movie?.voteAverage}") }
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
                imageVector = if (movie?.isVisible == true) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = "Down Arrow",
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(25.dp)
                    .clickable {
                        toggle()
                    }
                    .constrainAs(toggle) {
                        end.linkTo(parent.end)
                    },
                tint = Color.DarkGray
            )

            AnimatedVisibility(visible = movie?.isVisible ?: false,
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
                        withStyle(default) { append("${movie?.overview}\n\n") }
                        withStyle(bold) { append("Original language:") }
                        withStyle(default) { append("${movie?.originalLanguage}\n\n") }
                        withStyle(bold) { append("Release date:") }
                        withStyle(default) { append("${movie?.releaseDate}\n") }
                        withStyle(bold) { append("Popularity:") }
                        withStyle(default) { append("${movie?.popularity}") }
                    }, modifier = Modifier
                        .wrapContentHeight()
                        .padding(10.dp)
                )
            }
        }
    }
}
