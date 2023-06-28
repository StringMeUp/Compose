package com.sr.composemovies

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.sr.composemovies.navigation.MainNavigation
import com.sr.composemovies.navigation.NavigationItem
import com.sr.composemovies.ui.theme.ComposeMoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesApp()
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalAnimationApi::class)
@Composable
@Preview
fun MoviesApp(
    viewModel: MainViewModel = viewModel(),
) {
    ComposeMoviesTheme {
        //expose navController
        val navController = rememberAnimatedNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        when (navBackStackEntry?.destination?.route) {
            NavigationItem.Main.route -> {
                viewModel.setTopBarState(true)
            }

            NavigationItem.Detail.route -> {
                viewModel.setTopBarState(false)
            }
        }

        Scaffold(topBar = {
            /** Animating TopAppBar (show/hide) causes views on detail screens to jump.
             * I have found no clear solution yet.
            AnimateTopBar(viewModel, navController)*/

            if (viewModel.topBarState.value) AddTopAppBar(navController = navController)
        }) {
            Column {
                MainNavigation(navController = navController)
            }
        }
    }
}

@Composable
private fun AnimateTopBar(
    viewModel: MainViewModel,
    navController: NavHostController,
) {
    AnimatedVisibility(
        visible = viewModel.topBarState.value,
        enter = fadeIn(animationSpec = tween(700)),
        exit = fadeOut(animationSpec = tween(0))) {
        TopAppBar(
            modifier = Modifier
                .padding(5.dp)
                .shadow(0.dp),
            backgroundColor = Color.Transparent,
            elevation = 0.dp) {

            Card(
                elevation = 5.dp,
                shape = RoundedCornerShape(8.dp),
                backgroundColor = colorResource(id = R.color.s_color),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(3.dp)) {

                ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                    val (image, text) = createRefs()
                    val imageId =
                        NavigationItem.findNavItem(navController.currentDestination?.route).icon
                    val nameRes =
                        NavigationItem.findNavItem(navController.currentDestination?.route).name

                    Icon(painter = painterResource(id = imageId),
                        contentDescription = stringResource(id = nameRes),
                        modifier = Modifier
                            .padding(start = 14.dp)
                            .constrainAs(image) {
                                start.linkTo(parent.start)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
                    )

                    Text(text = stringResource(id = nameRes),
                        color = Color.Blue,
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
}

@Composable
fun AddTopAppBar(navController: NavHostController) {
    TopAppBar(
        modifier = Modifier
            .padding(5.dp)
            .shadow(0.dp),
        backgroundColor = Color.Transparent,
        elevation = 0.dp) {

        Card(
            elevation = 5.dp,
            shape = RoundedCornerShape(8.dp),
            backgroundColor = colorResource(id = R.color.s_color),
            modifier = Modifier
                .fillMaxSize()
                .padding(3.dp)) {

            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (image, text) = createRefs()
                val imageId =
                    NavigationItem.findNavItem(navController.currentDestination?.route).icon
                val nameRes =
                    NavigationItem.findNavItem(navController.currentDestination?.route).name

                Image(painter = painterResource(id = imageId),
                    contentDescription = stringResource(id = nameRes),
                    modifier = Modifier
                        .padding(start = 14.dp)
                        .constrainAs(image) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                )

                Text(text = stringResource(id = nameRes),
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


