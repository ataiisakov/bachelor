package com.example.benchmark

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until


fun MacrobenchmarkScope.waitForContent() {
    device.wait(Until.hasObject(By.text("HEADER TEXT")), TEN_MILLIS)
}

fun MacrobenchmarkScope.listScrollDownUp() {
    val list = device.findObject(By.res(packageName, RECYCLE_VIEW_RES_ID))
    list.setGestureMargin(device.displayWidth / 5)
    list.fling(Direction.DOWN)
    device.waitForIdle()
    list.fling(Direction.UP)
}

fun MacrobenchmarkScope.goToDetail() {
    val list = device.findObject(By.res(packageName, RECYCLE_VIEW_RES_ID))
    // Select first user from the list
    list.children[1].click()
    device.wait(Until.gone(By.res(packageName, RECYCLE_VIEW_RES_ID)), TEN_MILLIS)
}

fun MacrobenchmarkScope.detailScrollDownUp() {
    val detailScroll = device.findObject(By.res(packageName, DETAIL_SCROLL_CONTENT_RES_ID))
    detailScroll.fling(Direction.DOWN, SPEED_PIXELS)
    device.waitForIdle()
    detailScroll.fling(Direction.UP, SPEED_PIXELS)
}

fun MacrobenchmarkScope.pressBack() {
    device.pressBack()
}