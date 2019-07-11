package com.example.newsone2.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.newsone2.R

//OnFragmentInteractionListener чтобы получить доступ к активити с фрагмента
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
