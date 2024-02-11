package com.example.notesapp.feature_note.domain.util

import com.example.notesapp.feature_note.domain.model.Note

data class NoteStates(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSelectionVisible: Boolean = false
)
