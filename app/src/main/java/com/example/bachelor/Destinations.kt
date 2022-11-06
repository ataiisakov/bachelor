package com.example.bachelor

import androidx.navigation.NavType
import androidx.navigation.navArgument

object Destinations {
    const val HOME_ROUTE = "home"
    const val DETAIL_ROUTE = "detail"
}


sealed class Screen(val route: String) {

    object Overview : Screen(Destinations.HOME_ROUTE)

    object Detail : Screen(Destinations.DETAIL_ROUTE) {
        const val userIdTypeArg = "USER_ID_ARG"
        val routeWithArgs = "${route}/{$userIdTypeArg}"
        val args = listOf(navArgument(userIdTypeArg) { type = NavType.LongType })
    }
}