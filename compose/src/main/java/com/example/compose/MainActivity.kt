package com.example.compose

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.metrics.performance.JankStats
import com.example.bachelor.presentation.theme.MyCustomTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class MainActivity : AppCompatActivity() {
    private lateinit var jankStats: JankStats
    private val jankFrameListener = JankStats.OnFrameListener { frameData ->
        // A real app could do something more interesting, like writing the info to local storage and later on report it.
        if (frameData.isJank) {
            Log.e(
                "JankStatsSample",
                frameData.toString() + " MillisFrameDuration[${frameData.frameDurationUiNanos / 1e6f}]"
            )
        } else {
            Log.d(
                "JankStatsSample",
                frameData.toString() + " MillisFrameDuration[${frameData.frameDurationUiNanos / 1e6f}]"
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
        jankStats = JankStats.createAndTrack(window, jankFrameListener)
    }

    override fun onResume() {
        super.onResume()
        jankStats.isTrackingEnabled = true
    }

    override fun onPause() {
        super.onPause()
        jankStats.isTrackingEnabled = false
    }

    @OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
    @Composable
    fun App() {
        val metricsStateHolder = rememberMetricsStateHolder()
        metricsStateHolder.state?.putState("Activity", javaClass.simpleName)

        MyCustomTheme {
            val navController = rememberAnimatedNavController()
            Scaffold(
                modifier = Modifier.semantics {
                    testTagsAsResourceId = true
                }
            ) { innerPadding ->
                AppNavHost(navController = navController, modifier = Modifier.padding(innerPadding))
            }
        }
    }
}