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

class HeavyListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heavy_list)
        findViewById<RecyclerView>(R.id.recycler).adapter = HeavyListAdapter()
    }
}

class HeavyListAdapter : RecyclerView.Adapter<HeavyListAdapter.HeavyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeavyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return HeavyViewHolder(view)
    }

    override fun onBindViewHolder(holder: HeavyViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 300

    inner class HeavyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind() = trace("Bind list item") {
            with(itemView) {
                Thread.sleep(200)
                findViewById<TextView>(R.id.text).text = UUID.randomUUID().toString()
            }
        }
    }
}