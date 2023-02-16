package com.example.megagigasolusindoapplication.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.megagigasolusindoapplication.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // setup bottom navigation
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        val navController = findNavController(R.id.nav_fragment)
        bottomNav.setupWithNavController(navController)

    }


    // on back press fun
    override fun onBackPressed() {
        this.finish()
        super.onBackPressed()
    }

}