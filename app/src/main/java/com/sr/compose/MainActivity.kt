package com.sr.compose

import com.sr.compose.navigation.MainNavigation
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
            val viewModel: MainViewModel = viewModel()
            MoviesApp(
                isVisible = { viewModel.topBarState.value },
                setTopBar = { isVisible -> viewModel.setTopBarState(isVisible) })
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalAnimationApi::class)
@Composable
@Preview
fun MoviesApp(
    isVisible: () -> Boolean = { false },
    setTopBar: (isVisible: Boolean) -> Unit = {},
) {
    ComposeMoviesTheme {

        val navController = rememberAnimatedNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        when (navBackStackEntry?.destination?.route) {
            NavigationItem.Main.route -> {
                setTopBar(true)
            }

            NavigationItem.DefaultArgs.route -> {
                setTopBar(false)
            }
        }

        Scaffold(topBar = {
            AnimateTopBar(isVisible, navController)
        }) {
            Column {
                MainNavigation(navController = navController)
            }
        }
    }
}

@Composable
private fun AnimateTopBar(
    isVisible: () -> Boolean = { false },
    navController: NavHostController,
) {
    AnimatedVisibility(
        visible = isVisible(),
        enter = slideInVertically(initialOffsetY = { -it }, animationSpec = tween(1000)),
        exit = slideOutVertically(targetOffsetY = { -it }, animationSpec = tween(1000))
    ) {
        AddTopAppBar(
            getIcon = { NavigationItem.findNavItem(navController.currentDestination?.route).icon },
            getName = { NavigationItem.findNavItem(navController.currentDestination?.route).name }
        )
    }
}

@Composable
@Preview
fun AddTopAppBar(
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
        ) {

            ConstraintLayout(
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


