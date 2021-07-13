package com.marijannovak.macrobenchmarktest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.tracing.trace
import java.util.*

class LightListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_light_list)
        findViewById<RecyclerView>(R.id.recycler).adapter = LightListAdapter()
    }
}

class LightListAdapter : RecyclerView.Adapter<LightListAdapter.LightViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LightViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return LightViewHolder(view)
    }

    override fun onBindViewHolder(holder: LightViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 300

    inner class LightViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind() = trace("Bind list item") {
            with(itemView) {
                findViewById<TextView>(R.id.text).text = UUID.randomUUID().toString()
            }
        }
    }
}