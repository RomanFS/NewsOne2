package com.example.newsone2.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsone2.R
import com.example.newsone2.domain.NewsAdapter
import kotlinx.android.synthetic.main.fragment_fav.*

private var lastFirstVisiblePosition: Int = 0
private const val tableName = "fav"

class FavFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fav, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycle.layoutManager = LinearLayoutManager(context)
        recycle.adapter = NewsAdapter(context!!, tableName)
    }

    override fun onPause() {
        super.onPause()
        lastFirstVisiblePosition = (recycle.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
    }

    override fun onResume() {
        super.onResume()
        (recycle.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(lastFirstVisiblePosition,0)
    }
}
