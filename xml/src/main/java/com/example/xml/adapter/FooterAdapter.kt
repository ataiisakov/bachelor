package com.example.xml.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.xml.R


class FooterAdapter : RecyclerView.Adapter<FooterAdapter.FooterViewHolder>() {
    class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val headerTv: TextView = view.findViewById(R.id.header_tv)
        fun bind(position: Int) {
            headerTv.text = "Footer Text"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FooterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.header_list_item, parent, false)
        return FooterViewHolder(view)
    }

    override fun onBindViewHolder(holder: FooterViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = 1
}