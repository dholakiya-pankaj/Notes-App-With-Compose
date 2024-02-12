package com.example.notesapp.feature_note.presentation.add_edit_note

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent {
    data class TitleEntered(val title: String): AddEditNoteEvent()
    data class ChangedTitleFocus(val focusState: FocusState): AddEditNoteEvent()
    data class ContentEntered(val content: String): AddEditNoteEvent()
    data class ChangedContentFocus(val focusState: FocusState): AddEditNoteEvent()
    data class OnColorChanged(val newColor: Int): AddEditNoteEvent()
    object SaveNote: AddEditNoteEvent()
}
