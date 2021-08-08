package com.example.notesapp.login.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.login.appConstants.AppConstants
import com.example.notesapp.login.appConstants.AppConstants.Companion.FULL_NAME

class SplashActivity: AppCompatActivity() {

    lateinit var sharedPrefs : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPrefs = getSharedPreferences(AppConstants.SHARED_PREF, Context.MODE_PRIVATE)

        val intent = if (sharedPrefs.getBoolean(AppConstants.IS_LOGGED_IN, false)) {
            Intent(this, NotesAppActivity::class.java).apply {
                putExtra(FULL_NAME, sharedPrefs.getString(FULL_NAME, "MYNotesApp"))
            }
        } else {
            Intent(this, LoginActivity::class.java)
        }
        startActivity(intent)
    }
}