package com.example.benchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


private const val ITERATIONS = 1


@LargeTest
@RunWith(AndroidJUnit4::class)
class FrameTimingBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()


    @Test
    fun scrollCompilationNone() = scrollList(CompilationMode.None())


    private fun scrollList(compilationMode: CompilationMode) = benchmarkRule.measureRepeated(
        // [START_EXCLUDE]
        packageName = PACKAGE_NAME,
        metrics = listOf(FrameTimingMetric()),
        // Try switching to different compilation modes to see the effect
        // it has on frame timing metrics.
        compilationMode = compilationMode,
        startupMode = StartupMode.COLD, // restarts activity each iteration
        iterations = ITERATIONS,
        // [END_EXCLUDE]
        setupBlock = {
            pressHome()
            startActivityAndWait()
        }
    ) {
        waitForContent()
        listScrollDownUp()
        goToDetail()
        detailScrollDownUp()
    }

}