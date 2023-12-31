package com.sr.compose.ui.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.sr.compose.R
import com.sr.compose.navigation.Graph
import com.sr.compose.navigation.NavigationItem
import com.sr.compose.util.navigateBottomNavigationScreen
import timber.log.Timber

@Composable
fun BottomBar(
    currentRoute: String? = "",
    isVisible: Boolean = false,
    navController: NavHostController,
) {
    val items = NavigationItem.BottomNavMain.bottomNavDestinations()

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { it }, animationSpec = tween(1000)),
        exit = slideOutVertically(targetOffsetY = { it }, animationSpec = tween(1000))
    ) {

        BottomNavigation(
            backgroundColor = colorResource(id = R.color.s_color),
            contentColor = Color.Black
        ) {

            items.forEach { item ->
                BottomNavigationItem(
                    onClick = {
                        navController.navigateBottomNavigationScreen(item, navController.currentDestination?.route ?: "")
                    },
                    icon = {
                        Icon(
                            painterResource(id = item.icon),
                            contentDescription = item.route
                        )
                    },
                    label = {
                        Text(
                            text = LocalContext.current.getString(item.name),
                            fontSize = 9.sp
                        )
                    },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.LightGray.copy(0.4f),
                    alwaysShowLabel = true,
                    selected = currentRoute == item.route
                )
            }
        }
    }
}