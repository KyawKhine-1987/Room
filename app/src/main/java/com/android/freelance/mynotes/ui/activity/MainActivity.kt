package com.android.freelance.mynotes.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.android.freelance.mynotes.R

class MainActivity : AppCompatActivity() {

    private val LOG_TAG = MainActivity::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(LOG_TAG, "TEST : onCreate() is called...")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    //back instruction
    override fun onSupportNavigateUp(): Boolean {
        Log.i(LOG_TAG, "TEST : onSupportNavigateUp() is called...")

        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.fragment), null)
    }
}
