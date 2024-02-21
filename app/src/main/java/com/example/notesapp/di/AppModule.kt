package com.example.notesapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesapp.feature_note.data.database.NotesDatabase
import com.example.notesapp.feature_note.data.repository.NoteRepositoryImpl
import com.example.notesapp.feature_note.domain.repository.NoteRepository
import com.example.notesapp.feature_note.domain.usecase.AddNoteUseCase
import com.example.notesapp.feature_note.domain.usecase.DeleteNoteUseCase
import com.example.notesapp.feature_note.domain.usecase.GetNoteUseCase
import com.example.notesapp.feature_note.domain.usecase.GetNotesUseCase
import com.example.notesapp.feature_note.domain.usecase.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(
        @ApplicationContext context: Context
    ): NotesDatabase {
        return Room.databaseBuilder(
            context,
            NotesDatabase::class.java,
            NotesDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(database: NotesDatabase): NoteRepository {
        return NoteRepositoryImpl(database.noteDao())
    }

    @Provides
    @Singleton
    fun provideNotesUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotesUseCase = GetNotesUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository),
            addNoteUseCase = AddNoteUseCase(repository),
            getNoteUseCase = GetNoteUseCase(repository)
        )
    }
}