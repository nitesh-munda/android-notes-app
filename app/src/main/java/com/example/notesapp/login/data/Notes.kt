package com.example.notesapp.login.data

import com.example.notesapp.login.db.NotesRow

data class Notes constructor(
    val id: Int,
    val title: String,
    val description: String,
    val isTaskDone: Boolean
)
