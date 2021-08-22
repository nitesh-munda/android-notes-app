package com.example.notesapp.login.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.login.appConstants.AppConstants
import com.example.notesapp.login.appConstants.AppConstants.Companion.FULL_NAME
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class SplashActivity: AppCompatActivity() {

    lateinit var sharedPrefs : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPrefs = getSharedPreferences(AppConstants.SHARED_PREF, Context.MODE_PRIVATE)
        getFcmToken()
        val intent = if (sharedPrefs.getBoolean(AppConstants.IS_LOGGED_IN, false)) {
            Intent(this, NotesAppActivity::class.java).apply {
                putExtra(FULL_NAME, sharedPrefs.getString(FULL_NAME, "MYNotesApp"))
            }
        } else {
            Intent(this, LoginActivity::class.java)
        }
        startActivity(intent)
    }

    private fun getFcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d("SplashActivity", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            Log.d("SplashActivity", token.toString())
        })
    }
}