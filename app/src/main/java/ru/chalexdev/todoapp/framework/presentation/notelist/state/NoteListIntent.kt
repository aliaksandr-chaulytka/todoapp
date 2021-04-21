package ru.chalexdev.todoapp.framework.presentation.notelist.state

sealed class NoteListIntent {
    object FetchNoteList : NoteListIntent()
    data class AddNote(
        val id: String? = null,
        val title: String,
        val body: String? = null
    ) : NoteListIntent()
}