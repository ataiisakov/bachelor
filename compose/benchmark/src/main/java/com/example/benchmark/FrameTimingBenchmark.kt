package com.example.benchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


private const val ITERATIONS = 2


@LargeTest
@RunWith(AndroidJUnit4::class)
class FrameTimingBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun scrollComposeList() = benchmarkRule.measureRepeated(
        packageName = PACKAGE_NAME,
        metrics = listOf(FrameTimingMetric()),
        compilationMode = CompilationMode.None(),
        startupMode = StartupMode.WARM,
        iterations = ITERATIONS,
        setupBlock = {
            startActivityAndWait()
        }
    ) {
        device.wait(Until.findObject(By.text("Header Text")), 5_000)
        val column = device.findObject(By.res("lazyColumn"))
        column.setGestureMargin(device.displayWidth / 5)
        column.fling(Direction.DOWN)
        device.waitForIdle()
        column.fling(Direction.UP)

    }
}