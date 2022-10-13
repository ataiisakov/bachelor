package com.example.bachelor

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bachelor.model.UserRepository
import com.example.bachelor.presentation.compose.DetailScreen
import com.example.bachelor.presentation.compose.OverviewScreen


@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Overview.route,
        modifier = modifier
    ) {

        composable(
            route = Screen.Overview.route
        ) {
            OverviewScreen(
                onUserClick = { user ->
                    navController.navigateToDetailUser(user.id)
                }
            )
        }

        composable(
            route = Screen.Detail.routeWithArgs,
            arguments = Screen.Detail.args
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