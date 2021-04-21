package ru.chalexdev.todoapp.framework.presentation.notelist.state

import ru.chalexdev.todoapp.business.domain.model.Note

sealed class NoteListState {
    object Loading : NoteListState()
    data class NoteList(val notes: List<Note>) : NoteListState()
    data class NewNote(val note: Note) : NoteListState()
    data class Error(val error: String?) : NoteListState()
}