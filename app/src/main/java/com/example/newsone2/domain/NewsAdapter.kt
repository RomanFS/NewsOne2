package com.example.newsone2.domain

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsone2.R
import kotlinx.android.synthetic.main.news_item.view.*

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        // call fun in DataHolder
        return 100
    }

    override fun onBindViewHolder(view: ViewHolder, pos: Int) {
        view.itemView.text_view.text = pos.toString()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}