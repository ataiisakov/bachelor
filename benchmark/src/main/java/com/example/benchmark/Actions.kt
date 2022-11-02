package com.example.benchmark

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until


fun MacrobenchmarkScope.listScrollDownUp() {
    val list = device.findObject(By.res(packageName, RECYCLE_VIEW_RES_ID))
    // Set gesture margin to avoid triggering gesture navigation
    list.setGestureMargin(device.displayWidth / 5)
    list.fling(Direction.DOWN)
    device.waitForIdle()
    list.fling(Direction.UP)
}

fun MacrobenchmarkScope.goToDetail() {
    val list = device.findObject(By.res(packageName, RECYCLE_VIEW_RES_ID))
    // Select random user from the list
    list.click()
    device.waitForIdle()
}


fun MacrobenchmarkScope.waitForContent() {
    device.wait(Until.hasObject(By.res(packageName, RECYCLE_VIEW_RES_ID)), 30_000)
}

fun MacrobenchmarkScope.detailScrollDownUp() {
    val detailScroll = device.findObject(By.scrollable(true))
    detailScroll.fling(Direction.DOWN)
    device.waitForIdle()
    detailScroll.fling(Direction.UP)
}