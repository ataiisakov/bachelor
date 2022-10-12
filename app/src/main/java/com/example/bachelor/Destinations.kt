package com.example.bachelor

import androidx.navigation.NavType
import androidx.navigation.navArgument

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

        const val userId = "USER_ID_ARG"
        val routeWithArgs = "${route}/{$userId}"
        val args = listOf(
            navArgument(userId) { type = NavType.LongType }
        )
    }
}