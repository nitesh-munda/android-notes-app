package com.example.notesapp.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.notesapp.databinding.ActivityLoginBinding
import com.example.notesapp.login.appConstants.AppConstants.Companion.FULL_NAME
import com.example.notesapp.login.appConstants.AppConstants.Companion.IS_LOGGED_IN
import com.example.notesapp.login.appConstants.AppConstants.Companion.SHARED_PREF
import com.example.notesapp.login.appConstants.AppConstants.Companion.USER_NAME

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    lateinit var sharedPrefs : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPrefs = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)

        setupListeners()
    }

    private fun setupListeners() {
        val name = binding.etFullName.text
        val username = binding.etUserName.text
        binding.buttonLayout.setOnClickListener {
            if (name.isNullOrEmpty() || username.isNullOrEmpty()) {
                Toast.makeText(this, "Name or Username can't be empty!", Toast.LENGTH_SHORT).show()
            } else {

                sharedPrefs.edit {
                    putString(FULL_NAME, name.toString())
                    putString(USER_NAME, username.toString())
                    putBoolean(IS_LOGGED_IN, true)
                }

                val intent = Intent(this, NotesAppActivity::class.java)
                intent.putExtra(FULL_NAME, name)
                intent.putExtra(USER_NAME, username)
                startActivity(intent)
            }
        }
    }
}