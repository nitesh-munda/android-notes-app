package com.example.notesapp.login.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NotesRow::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun getNotesRowDao() : NotesDao

    companion object {
        lateinit var INSTANCE : NotesDatabase
        fun getDatabase(context: Context) : NotesDatabase {
            synchronized(NotesDatabase::class.java) {
                if (::INSTANCE.isInitialized.not()) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, NotesDatabase::class.java, "NotesRoomDatabase")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return INSTANCE
            }
        }
    }

}