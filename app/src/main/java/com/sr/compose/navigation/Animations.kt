package com.sr.compose.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentScope<NavBackStackEntry>.popExitSlideOutToRight() =
    slideOutOfContainer(
        AnimatedContentScope.SlideDirection.Right,
        animationSpec = tween(500)
    )

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentScope<NavBackStackEntry>.popEnterSlideInFromRight() =
    slideIntoContainer(
        AnimatedContentScope.SlideDirection.Right,
        animationSpec = tween(500)
    )

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentScope<NavBackStackEntry>.enterSlideInFromLeft() =
    slideIntoContainer(
        AnimatedContentScope.SlideDirection.Left,
        animationSpec = tween(500)
    )

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentScope<NavBackStackEntry>.exitSlideOutToLeft() =
    slideOutOfContainer(
        AnimatedContentScope.SlideDirection.Left,
        animationSpec = tween(500)
    )
