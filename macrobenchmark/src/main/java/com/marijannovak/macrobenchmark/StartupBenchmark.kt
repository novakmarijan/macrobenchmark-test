package com.marijannovak.macrobenchmark

import android.content.Intent
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StartupBenchmark {

    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    companion object {
        const val PACKAGE_NAME = "com.marijannovak.macrobenchmarktest"
        const val HEAVY_ACTIVITY = "HeavyStartupActivity"
        const val LIGHT_ACTIVITY = "LightStartupActivity"
    }

    @Test
    fun coldHeavyStartup() = startupTest(StartupMode.COLD, HEAVY_ACTIVITY)

    @Test
    fun warmHeavyStartup() = startupTest(StartupMode.WARM, HEAVY_ACTIVITY)

    @Test
    fun hotHeavyStartup() = startupTest(StartupMode.HOT, HEAVY_ACTIVITY)

    @Test
    fun coldLightStartup() = startupTest(StartupMode.COLD, LIGHT_ACTIVITY)

    @Test
    fun warmLightStartup() = startupTest(StartupMode.WARM, LIGHT_ACTIVITY)

    @Test
    fun hotLightStartup() = startupTest(StartupMode.HOT, LIGHT_ACTIVITY)

    private fun startupTest(
        startupMode: StartupMode,
        activity: String
    ) = benchmarkRule.measureRepeated(
        packageName = PACKAGE_NAME,
        metrics = listOf(StartupTimingMetric()),
        iterations = 3,
        startupMode = startupMode
    ) {
        // Reset app state by pressing home button
        pressHome()

        // Action to run specific activity
        val intent = Intent().apply {
            setClassName(PACKAGE_NAME, "$PACKAGE_NAME.$activity")
        }

        // Run the activity and wait for it to become idle
        startActivityAndWait(intent)
    }
}
