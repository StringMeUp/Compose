package com.sr.compose.ui.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.sr.compose.R
import com.sr.compose.navigation.NavigationItem

@Composable
fun TopBar(
    isVisible: () -> Boolean = { false },
    navController: NavHostController,
) {
    AnimatedVisibility(
        visible = isVisible(),
        enter = slideInVertically(initialOffsetY = { -it }, animationSpec = tween(1000)),
        exit = slideOutVertically(targetOffsetY = { -it }, animationSpec = tween(1000))
    ) {
        Toolbar(
            getIcon = { NavigationItem.findNavItem(navController.currentDestination?.route).icon },
            getName = { NavigationItem.findNavItem(navController.currentDestination?.route).name }
        )
    }
}

@Composable
@Preview
fun Toolbar(
    getIcon: () -> Int = { R.drawable.ic_boat },
    getName: () -> Int = { R.string.app_name },
) {
    TopAppBar(
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        modifier = Modifier
            .padding(bottom = 12.dp, start = 5.dp, top = 5.dp, end = 5.dp)
            .shadow(0.dp)
    ) {
        Card(
            elevation = 5.dp,
            shape = RoundedCornerShape(8.dp),
            backgroundColor = colorResource(id = R.color.s_color),
            modifier = Modifier
                .fillMaxSize()
                .padding(3.dp)
        ) { ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (image, text) = createRefs()

                Image(
                    painter = painterResource(id = getIcon()),
                    contentDescription = stringResource(id = getName()),
                    modifier = Modifier
                        .padding(start = 14.dp)
                        .constrainAs(image) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                )

                Text(text = stringResource(id = getName()),
                    color = Color.White,
                    modifier = Modifier
                        .padding(8.dp)
                        .constrainAs(text) {
                            start.linkTo(image.end)
                            top.linkTo(image.top)
                            bottom.linkTo(image.bottom)
                        })
            }
        }
    }
}