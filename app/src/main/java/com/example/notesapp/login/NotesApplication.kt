package com.example.notesapp.login

import android.app.Application
import com.example.notesapp.login.db.NotesDatabase

class NotesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

    fun getDatabase() : NotesDatabase {
        return NotesDatabase.getDatabase(this)
    }
}