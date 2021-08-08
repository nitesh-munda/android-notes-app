package com.example.notesapp.login.view.recyclerview

import com.example.notesapp.login.db.NotesRow

interface NotesClickListener {
    fun onTaskChecked(notesRow: NotesRow)
}