package com.marijannovak.macrobenchmarktest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    // Simulate heavy initialization
    init {
        Thread.sleep(500)
    }

    private val list = List(100_000) { "number $it" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}