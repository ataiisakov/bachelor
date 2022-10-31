package com.example.benchmark

import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random


@RunWith(AndroidJUnit4::class)
class BaselineProfileGenerator {

    @get:Rule
    val rule = MacrobenchmarkRule()

    @Test
    fun generate() {
        rule.measureRepeated(
            packageName = "com.example.bachelor.xml",
            metrics = listOf(FrameTimingMetric()),
            iterations = 1,
            startupMode = StartupMode.COLD,
        ){
            startApp() // TODO Implement
            scroll() // TODO Implement
            goToDetail() // TODO Implement
        }
    }
}

fun MacrobenchmarkScope.startApp() {
    pressHome()
    startActivityAndWait()
}

fun MacrobenchmarkScope.scroll() {
    val list = device.findObject(By.res(packageName,"recycleView"))
    // Set gesture margin to avoid triggering gesture navigation
    list.setGestureMargin(device.displayWidth / 5)
    list.fling(Direction.DOWN)
    device.waitForIdle()
    list.fling(Direction.UP)
}

fun MacrobenchmarkScope.goToDetail() {
    val list = device.findObject(By.res(packageName,"recycleView"))
    val snacks = list.children
    // Select random snack from the list
    snacks[Random.nextInt(snacks.size)].click()
    // Wait until the screen is gone = the detail is shown
    device.wait(Until.gone(By.res(packageName,"recycleView")), 5_000)
    val detailScroll = device.findObject(By.scrollable(true))
    detailScroll.fling(Direction.DOWN)
    device.waitForIdle()
    detailScroll.fling(Direction.UP)
    device.pressBack()
}