package com.example.newsone2.ui

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.newsone2.R
import com.example.newsone2.dao.WebPages
import kotlinx.android.synthetic.main.activity_inner.*
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import java.io.File
import java.io.File.separator

private const val TAG = "InnerActivity"
private const val hashDir = "webPages"

class InnerActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inner)
        val url = intent.extras!!.getString("url")!!
        val favState = intent.extras!!.getBoolean("favState")

        web_view.settings.javaScriptEnabled = true

        if (favState) {
            web_view.loadUrl(url)
            return
        }

        val hash = DigestUtils.sha1(url)
        val hashName = String(Hex.encodeHex(hash)) + ".mht"
        hashPage(hashName, url)
    }

    private fun hashPage(hashName: String, url: String) {
        val client = WebPages(hashName, filesDir, hashDir)
        web_view.webViewClient = client
        var file = File(filesDir.absolutePath + separator + hashDir)
        file.mkdirs()

        file = File(file, hashName)
        if (file.exists()) {
            Log.d(TAG, "onCreate: tryLoad $hashName")
            web_view.loadUrl("file:///" + file.path)
        } else {
            Log.d(TAG, "onCreate: fail $hashName")
            web_view.loadUrl(url)
        }
    }
}
