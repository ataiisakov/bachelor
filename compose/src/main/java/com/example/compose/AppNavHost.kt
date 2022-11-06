package com.example.compose


import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.bachelor.model.UserRepository
import com.example.compose.ui.DetailScreen
import com.example.compose.ui.OverviewScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Overview.route,
        modifier = modifier
    ) {
        composable(
            route = Screen.Overview.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Screen.Detail.routeWithArgs -> {
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )
                    }
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Screen.Detail.routeWithArgs -> {
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )
                    }
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    Screen.Detail.routeWithArgs -> {
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )
                    }
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    Screen.Detail.routeWithArgs -> {
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )
                    }
                    else -> null
                }
            }
        ) {
            OverviewScreen(
                onUserClick = { user ->
                    navController.navigateToDetailUser(user.id)
                }
            )
        }

        composable(
            route = Screen.Detail.routeWithArgs,
            arguments = Screen.Detail.args,
            enterTransition = {
                when (initialState.destination.route) {
                    Screen.Overview.route -> {
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )
                    }
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Screen.Overview.route -> {
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )
                    }
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    Screen.Overview.route -> {
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )
                    }
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    Screen.Overview.route -> {
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )
                    }
                    else -> null
                }
            }
        ) {
            val userId = it.arguments?.getLong(Screen.Detail.userIdTypeArg)
            val user = UserRepository.getUserById(userId!!)
            DetailScreen(user)
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) {
    return navigate(route) { launchSingleTop = true }
}

private fun NavHostController.navigateToDetailUser(userId: Long) {
    return navigateSingleTopTo("${Screen.Detail.route}/${userId}")
}