package com.dicoding.asclepius.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.dicoding.asclepius.R
import com.dicoding.asclepius.view.analyze.AnalyzeFragment
import com.dicoding.asclepius.view.history.HistoryFragment
import com.dicoding.asclepius.view.news.NewsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView  // Bottom navigation view

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        // Opens AnalyzeFragment by default if there is no saved instance state
        if (savedInstanceState == null) {
            openFragment(AnalyzeFragment())
        }

        // Sets up listener to handle navigation item selection
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_analyze -> {
                    openFragment(AnalyzeFragment())
                    true
                }
                R.id.nav_news -> {
                    openFragment(NewsFragment())
                    true
                }
                R.id.nav_history -> {
                    openFragment(HistoryFragment())
                    true
                }
                else -> false
            }
        }
    }

    // Replaces the current fragment with the given fragment
    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
