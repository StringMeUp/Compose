package com.sr.compose

import MainNavigation
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.accompanist.insets.ui.Scaffold
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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

import com.sr.compose.navigation.NavigationItem
import com.sr.compose.ui.theme.ComposeMoviesTheme
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
            AnimateTopBar(viewModel, navController)
        }) {
            Column(modifier = Modifier.padding(top = 70.dp)) {
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
        enter = slideInVertically(initialOffsetY = { -it }, animationSpec = tween(1000)),
        exit = slideOutVertically(targetOffsetY = { -it }, animationSpec = tween(1000))) {
        AddTopAppBar(navController = navController)
    }
}

@Composable
fun AddTopAppBar(navController: NavHostController) {
    TopAppBar(
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        modifier = Modifier
            .padding(bottom = 12.dp, start = 5.dp, top = 5.dp, end = 5.dp)
            .shadow(0.dp)) {

        Card(
            elevation = 5.dp,
            shape = RoundedCornerShape(8.dp),
            backgroundColor = colorResource(id = R.color.s_color),
            modifier = Modifier
                .fillMaxSize()
                .padding(3.dp)) {

            ConstraintLayout(
                modifier = Modifier.fillMaxSize()) {
                val (image, text) = createRefs()
                val imageId =
                    NavigationItem.findNavItem(navController.currentDestination?.route).icon
                val nameRes =
                    NavigationItem.findNavItem(navController.currentDestination?.route).name

                Image(
                    painter = painterResource(id = imageId),
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


