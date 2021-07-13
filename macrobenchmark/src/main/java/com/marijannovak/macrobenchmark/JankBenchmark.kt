package com.marijannovak.macrobenchmark

import android.content.Intent
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.UiDevice
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class JankBenchmark {

    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    companion object {
        const val PACKAGE_NAME = "com.marijannovak.macrobenchmarktest"
        const val HEAVY_ACTIVITY = "HEAVY_LIST"
        const val LIGHT_ACTIVITY = "LIGHT_LIST"
        const val LIST_ID = "recycler"
    }

    @Test
    fun lightListTest() = jankTest(LIGHT_ACTIVITY)

    @Test
    fun heavyListTest() = jankTest(HEAVY_ACTIVITY)

    private fun jankTest(
        activity: String
    ) {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val device = UiDevice.getInstance(instrumentation)
        benchmarkRule.measureRepeated(
            packageName = PACKAGE_NAME,
            metrics = listOf(FrameTimingMetric()),
            iterations = 3,
            setupBlock = {
                // Action to run specific activity
                val intent = Intent().apply {
                    action = "${PACKAGE_NAME}.$activity"
                }

                // Run the activity and wait for it to become idle
                startActivityAndWait(intent)
            },
            measureBlock = {
                // Find list and scroll it
                val recycler = device.findObject(By.res(PACKAGE_NAME, LIST_ID))

                // Prevent accidental navigation gestures
                recycler.setGestureMargin(device.displayWidth / 5)

                for (i in 1..5) {
                    // Scroll 5% and wait for idle
                    recycler.scroll(Direction.DOWN, 5f)
                    device.waitForIdle()
                }
            }
        )
    }
}