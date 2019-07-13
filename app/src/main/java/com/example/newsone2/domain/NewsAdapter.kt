package com.example.newsone2.domain

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsone2.R
import com.example.newsone2.dao.DataHolder
import com.example.newsone2.ui.InnerActivity
import com.example.newsone2.ui.MainActivity
import kotlinx.android.synthetic.main.news_item.view.*

private const val TAG = "NewsAdapter"

class NewsAdapter(val context: Context, private val tableName: String): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private val db = DataHolder(context, null, null, 1)
    private val newsList = db.findNews(tableName)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(view: ViewHolder, pos: Int) {
        //val newsObject = newsList[pos+1]
        val itemView = view.itemView

        //db.updateNews(tableName, ArrayList<NewsObject>())

        //db.addNews(NewsObject("h", "h", "h", "h", "h", "h", "h", "h", false), tableName)

        itemView.text_view.text = pos.toString()

        val getURLfromNewsObject = ""
        val getState = true

        itemView.see_more.setOnClickListener {
            val intent = Intent(context, InnerActivity::class.java)
            intent.putExtra("url", getURLfromNewsObject)
            intent.putExtra("favState", getState)
            context.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int {
        //Log.d(TAG, "getItemCount: " + db.getItemCount(tableName))
        return 10
    }
}