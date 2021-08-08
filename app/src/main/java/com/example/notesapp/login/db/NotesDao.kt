package com.example.notesapp.login.db

import androidx.room.*

@Dao
interface NotesDao {

    @Query("SELECT * FROM NotesApp")
    fun getAllNotes() : List<NotesRow>

    @Insert
    fun insertNotes(notesRow: NotesRow)

    @Update
    fun updateNotes(notesRow: NotesRow)

    @Delete
    fun deleteNotes(notesRow: NotesRow)
}