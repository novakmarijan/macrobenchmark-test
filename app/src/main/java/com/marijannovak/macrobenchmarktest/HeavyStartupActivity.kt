package com.marijannovak.macrobenchmarktest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.tracing.trace
import java.util.*
import kotlin.random.Random

class HeavyStartupActivity : AppCompatActivity() {

    // Simulate heavy initialization
    init {
        createAList()
    }

    private lateinit var list: List<TestData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heavy_startup)
    }

    private fun createAList() = trace("Creating list") {
        list = List(100_000) { TestData() }
    }
}

data class TestData(
    val name: String = UUID.randomUUID().toString(),
    val number: Int = Random.Default.nextInt()
)