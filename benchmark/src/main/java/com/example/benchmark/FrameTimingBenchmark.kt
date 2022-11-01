package com.example.benchmark

import android.content.Intent
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


private const val ITERATIONS = 1



@LargeTest
@RunWith(AndroidJUnit4::class)
class FrameTimingBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    // [START macrobenchmark_control_your_app]
    @Test
    fun scrollList() {
        benchmarkRule.measureRepeated(
            // [START_EXCLUDE]
            packageName =  "com.example.bachelor.xml",
            metrics = listOf(FrameTimingMetric()),
            // Try switching to different compilation modes to see the effect
            // it has on frame timing metrics.
            compilationMode = CompilationMode.None(),
            startupMode = StartupMode.WARM, // restarts activity each iteration
            iterations = ITERATIONS,
            // [END_EXCLUDE]
            setupBlock = {
                // Before starting to measure, navigate to the UI to be measured
                startActivityAndWait()
            }
        ) {
            val recycler = device.findObject(By.res(packageName, "recycleView"))
            // Set gesture margin to avoid triggering gesture navigation
            // with input events from automation.
            recycler.setGestureMargin(device.displayWidth / 5)

            // Scroll down several times
            recycler.fling(Direction.DOWN)

            // Wait for the scroll to finish
            device.waitForIdle()
        }
    }
    // [END macrobenchmark_control_your_app]
}