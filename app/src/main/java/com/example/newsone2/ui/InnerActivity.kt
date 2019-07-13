package com.example.newsone2.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.newsone2.R

private const val TAG = "InnerActivity"

class InnerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_inner)
        val url = intent.extras!!.getString("url")!!
        val favState = intent.extras!!.getBoolean("favState")
    }
}
