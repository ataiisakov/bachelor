package com.example.benchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MacrobenchmarkScope
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
        listScrollDownUp()
    }


    @Test
    fun interactionComposeApp() = appInteraction(CompilationMode.None())


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

}

fun MacrobenchmarkScope.waitForContent() {
    device.wait(Until.hasObject(By.text("Header Text")), TEN_MILLIS)
}

fun MacrobenchmarkScope.listScrollDownUp() {
    val column = device.findObject(By.res(LAZY_COLUMN_RES_ID))
    column.setGestureMargin(device.displayWidth / 5)

    column.fling(Direction.DOWN)
    device.waitForIdle()
    column.fling(Direction.UP)
}

fun MacrobenchmarkScope.goToDetail() {
    val list = device.findObject(By.res(LAZY_COLUMN_RES_ID))
    // Select first user from the list
    list.children[1].click()
    device.wait(Until.gone(By.res(LAZY_COLUMN_RES_ID)), TEN_MILLIS)
}

fun MacrobenchmarkScope.detailScrollDownUp() {
    val detailScroll = device.findObject(By.res(DETAIL_SCROLL_CONTENT_RES_ID))
    detailScroll.fling(Direction.DOWN, SPEED_PIXELS)
    device.waitForIdle()
    detailScroll.fling(Direction.UP, SPEED_PIXELS)
}

fun MacrobenchmarkScope.pressBack() {
    device.pressBack()
}