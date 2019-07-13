package com.example.newsone2.ui

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.example.newsone2.R
import com.example.newsone2.ui.fragments.*
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"
//OnFragmentInteractionListener чтобы получить доступ к активити с фрагмента
class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replace(EmailedFragment())

        bar.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.emailed -> {
                Log.d(TAG, "Emailed")
                replace(EmailedFragment())
                return true
            }
            R.id.shared -> {
                Log.d(TAG, "Shared")
                replace(SharedFragment())
                return true
            }
            R.id.viewed -> {
                Log.d(TAG, "Viewed")
                replace(ViewedFragment())
                return true
            }
            R.id.fav -> {
                Log.d(TAG, "Fav")
                replace(FavFragment())
                return true
            }
            else -> Log.e(TAG, "Unknown item clicked")
        }
        return false
    }

    fun replace(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment, null)
        transaction.commit()
    }
}
