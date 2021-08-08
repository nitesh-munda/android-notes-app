package com.example.notesapp.login.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notesapp.login.appConstants.DatabaseConstants.NOTES_TABLE

@Entity(tableName = NOTES_TABLE)
data class NotesRow constructor(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "image_path")
    var imagePath: String?="",
    @ColumnInfo(name = "is_task_done")
    var isTaskDone: Boolean=false
)
