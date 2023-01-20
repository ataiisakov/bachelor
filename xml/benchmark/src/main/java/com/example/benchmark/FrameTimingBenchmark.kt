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


private const val ITERATIONS = 2


@LargeTest
@RunWith(AndroidJUnit4::class)
class FrameTimingBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()


    @Test
    fun interactionApp() = appInteraction(CompilationMode.None())


    @Test
    fun scrollXmlList() = benchmarkRule.measureRepeated(
        packageName = PACKAGE_NAME,
        metrics = listOf(FrameTimingMetric()),
        compilationMode = CompilationMode.None(),
        startupMode = StartupMode.WARM,
        iterations = ITERATIONS,
        setupBlock = {
            startActivityAndWait()
        }
    ) {
        listScrollDownUp()
    }

    private fun appInteraction(compilationMode: CompilationMode) = benchmarkRule.measureRepeated(
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
        listScrollDownUp()
        goToDetail()
        detailScrollDownUp()
        pressBack()
    }

    @Test
    fun appInteraction() = benchmarkRule.measureRepeated(
        packageName = PACKAGE_NAME,
        metrics = listOf(FrameTimingMetric()),
        iterations = 2,
        startupMode = StartupMode.WARM,
        setupBlock = {
            pressHome()
            startActivityAndWait()
        }
    ) {
        listScrollDownUp()
    }

    @Test
    fun goToDetail() = benchmarkRule.measureRepeated(
        packageName = PACKAGE_NAME,
        metrics = listOf(FrameTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD,
        setupBlock = {
            pressHome()
            startActivityAndWait()
        }
    ) {
        waitForContent()
        goToDetail()
    }

    @Test
    fun goToDetailScroll() = benchmarkRule.measureRepeated(
        packageName = PACKAGE_NAME,
        metrics = listOf(FrameTimingMetric()),
        iterations = 1,
        startupMode = StartupMode.COLD,
        setupBlock = {
            pressHome()
            startActivityAndWait()
        }
    ) {
        goToDetail()
        detailScrollDownUp()
    }

}