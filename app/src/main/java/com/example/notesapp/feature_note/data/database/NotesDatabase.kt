package com.example.notesapp.feature_note.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notesapp.feature_note.data.source.NoteDao
import com.example.notesapp.feature_note.domain.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NotesDatabase: RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}