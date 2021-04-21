package ru.chalexdev.todoapp.business.data.cache.abstraction

import ru.chalexdev.todoapp.business.domain.model.Note

interface NoteCacheDataSource {

    suspend fun getAllNotes(): List<Note>

    suspend fun insertNote(note: Note): Long

    suspend fun insertNotes(notes: List<Note>): LongArray

    suspend fun deleteNote(id: String): Int

    suspend fun deleteNotes(notes: List<Note>): Int

    suspend fun updateNote(id: String, newTitle: String, newBody: String): Int

    suspend fun searchNoteById(id: String): Note?
}