package com.example.bachelor

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bachelor.model.User
import com.example.bachelor.model.UserRepository
import com.example.bachelor.presentation.compose.DetailScreen
import com.example.bachelor.presentation.compose.OverviewScreen
import com.example.bachelor.presentation.theme.MyCustomTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           App()
        }
    }

    @Composable
    fun App() {
        MyCustomTheme {
            var currScreen: Screen by remember { mutableStateOf(Screen.Overview) }
            val navController = rememberNavController()
            Scaffold() { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = Screen.Overview.route,
                    modifier = Modifier.padding(innerPadding)
                ){
                    composable(route = Screen.Overview.route) {
                        OverviewScreen(
                            onUserClick = {
                                navController.navigate(
                                    "${Screen.Detail.route}/${it.id}"
                                ) {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                    composable(
                        route = Screen.Detail.routeWithArgs,
                        arguments = Screen.Detail.args
                    ) {
                        val userId = it.arguments?.getLong(Screen.Detail.navArg)
                        val user = UserRepository.getUserById(userId!!)
                        DetailScreen(user)
                    }
                }
            }
        }
    }
/*
    override fun showDetails(user: User, view: View?) {
        view ?: return

        val detailFragment = DetailFragment.newInstance(user)
        val homeFragment = supportFragmentManager.fragments.last()

        detailFragment.sharedElementEnterTransition = TransitionInflater.from(this)
            .inflateTransition(R.transition.default_transition)
        detailFragment.sharedElementReturnTransition = TransitionInflater.from(this)
            .inflateTransition(R.transition.default_transition)
        detailFragment.enterTransition = Fade()

        val photo = view.findViewById<ImageView>(R.id.userPhotoImageView)
        val name = view.findViewById<TextView>(R.id.userNameTextView)

        homeFragment.exitTransition = Fade()
        homeFragment.reenterTransition = Fade()

        supportFragmentManager
            .commit {
                setReorderingAllowed(true)

                addSharedElement(
                    photo,
                    String.format(
                        resources.getString(R.string.shared_image_transition),
                        user.id
                    )
                )
                addSharedElement(
                    name,
                    String.format(
                        resources.getString(R.string.shared_name_transition),
                        user.id
                    )
                )
                replace(R.id.fragmentContainer, detailFragment)
                addToBackStack(null)
            }
    }*/
}