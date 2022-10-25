package com.example.bachelor

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.bachelor.presentation.theme.MyCustomTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           App()
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun App() {
        MyCustomTheme {
            val navController = rememberAnimatedNavController()
            Scaffold { innerPadding ->
                AppNavHost(navController = navController, modifier = Modifier.padding(innerPadding))
            }
        }
    }
}