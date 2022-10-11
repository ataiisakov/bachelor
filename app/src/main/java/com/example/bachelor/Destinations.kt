package com.example.bachelor

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.bachelor.presentation.compose.DetailScreen
import com.example.bachelor.presentation.compose.OverviewScreen

object Destinations {
    const val HOME_ROUTE = "home"
    const val DETAIL_ROUTE = "detail"
}


sealed class Screen {
    abstract val route: String

    object Overview : Screen() {
        override val route: String = Destinations.HOME_ROUTE
    }

    object Detail : Screen() {
        override val route: String = Destinations.DETAIL_ROUTE

        const val navArg = "USER_ID_ARG"
        val routeWithArgs = "${route}/{$navArg}"
        val args = listOf(
            navArgument(navArg) {type= NavType.LongType}
        )
    }
}