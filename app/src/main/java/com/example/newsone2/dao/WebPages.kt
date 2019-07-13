package com.example.newsone2.dao

import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import java.io.File
import java.io.File.separator

private const val TAG = "WebPages"

class WebPages(private val hashName: String, private val filesDir: File, private val hashDir: String): WebViewClient() {
    override fun onPageStarted(
        view: WebView, url: String,
        favicon: Bitmap?
    ) {}

    override fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)
        Log.d(TAG, "onPageFinished: ")
        view.saveWebArchive(
            filesDir.absolutePath + separator + hashDir + separator + hashName
        )
    }

    override fun onLoadResource(view: WebView, url: String) {}
}