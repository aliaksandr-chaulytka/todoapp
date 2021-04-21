package ru.chalexdev.todoapp.framework.presentation.notelist.state

sealed class NoteListIntent {
    object FetchNoteList : NoteListIntent()
}