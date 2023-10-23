package com.sr.compose.util

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

fun AnimatedContentTransitionScope<NavBackStackEntry>.popExitSlideOutToRight() =
    slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(500)
    )

fun AnimatedContentTransitionScope<NavBackStackEntry>.popEnterSlideInFromRight() =
    slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(500)
    )

fun AnimatedContentTransitionScope<NavBackStackEntry>.enterSlideInFromLeft() =
    slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(500)
    )

fun AnimatedContentTransitionScope<NavBackStackEntry>.exitSlideOutToLeft() =
    slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(500)
    )