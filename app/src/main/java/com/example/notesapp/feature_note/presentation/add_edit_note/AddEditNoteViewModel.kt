package com.example.notesapp.feature_note.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.feature_note.domain.model.InvalidNoteException
import com.example.notesapp.feature_note.domain.model.Note
import com.example.notesapp.feature_note.domain.usecase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val notesUseCases: NoteUseCases
): ViewModel() {

    private val _titleState = mutableStateOf(NoteTextFieldState(hint = "Enter title..."))
    val titleState: State<NoteTextFieldState> = _titleState

    private val _contentState = mutableStateOf(NoteTextFieldState(hint = "Enter some content..."))
    val contentState: State<NoteTextFieldState> = _contentState

    private val _colorState = mutableIntStateOf(Note.noteColors.random().toArgb())
    val colorState: State<Int> = _colorState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    fun handleEvent(event: AddEditNoteEvent) {
        when(event) {
            is AddEditNoteEvent.TitleEntered -> {
                _titleState.value = titleState.value.copy(
                    text = event.title
                )
            }
            is AddEditNoteEvent.ChangedTitleFocus -> {
                _titleState.value = titleState.value.copy(
                    isHintVisible = !event.focusState.isFocused && titleState.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.ContentEntered -> {
                _contentState.value = contentState.value.copy(
                    text = event.content
                )
            }
            is AddEditNoteEvent.ChangedContentFocus -> {
                _contentState.value = contentState.value.copy(
                    isHintVisible = !event.focusState.isFocused && contentState.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.OnColorChanged -> {
                _colorState.value = event.newColor
            }
            is AddEditNoteEvent.SaveNote -> {
                saveNote()
            }
        }
    }

    private fun saveNote() {
        viewModelScope.launch {
            try {
                notesUseCases.addNoteUseCase(
                    Note(
                        id = currentNoteId,
                        title = titleState.value.text,
                        content = contentState.value.text,
                        timeStamp = System.currentTimeMillis(),
                        color = colorState.value
                    )
                )
                _eventFlow.emit(UiEvent.ShowSnackbar("Note inserted successfully."))
            }catch (e: InvalidNoteException) {
                _eventFlow.emit(UiEvent.ShowSnackbar(e.message ?: "Couldn't save note!"))
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object OnNoteSaved: UiEvent()
    }

}