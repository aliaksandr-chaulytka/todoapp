package ru.chalexdev.todoapp.business.data.cache.implementation

import ru.chalexdev.todoapp.business.data.cache.abstraction.NoteCacheDataSource
import ru.chalexdev.todoapp.business.domain.model.Note
import ru.chalexdev.todoapp.framework.datasource.cache.abstraction.NoteDaoService


class NoteCacheDataSourceImpl(private val noteDaoService: NoteDaoService) : NoteCacheDataSource {

    override suspend fun getAllNotes(): List<Note> = noteDaoService.getAllNotes()

    override suspend fun insertNote(note: Note) = noteDaoService.insertNote(note)

    override suspend fun insertNotes(notes: List<Note>) = noteDaoService.insertNotes(notes)

    override suspend fun deleteNote(id: String) = noteDaoService.deleteNote(id)

    override suspend fun deleteNotes(notes: List<Note>) = noteDaoService.deleteNotes(notes)

    override suspend fun updateNote(id: String, newTitle: String, newBody: String) =
        noteDaoService.updateNote(id, newTitle, newBody)

    override suspend fun searchNoteById(id: String) = noteDaoService.searchNoteById(id)
}