package ru.chalexdev.todoapp.business.data.network.implementation

import ru.chalexdev.todoapp.business.data.network.abstraction.NoteNetworkDataSource
import ru.chalexdev.todoapp.business.domain.model.Note
import ru.chalexdev.todoapp.framework.datasource.network.abstraction.NoteFirestoreDataSource

class NoteNetworkDataSourceImpl(private val firestoreDataSource: NoteFirestoreDataSource) :
    NoteNetworkDataSource {

    override suspend fun getAllNotes() = firestoreDataSource.getAllNotes()

    override suspend fun insertNote(note: Note) = firestoreDataSource.insertNote(note)

    override suspend fun insertNotes(notes: List<Note>) = firestoreDataSource.insertNotes(notes)

    override suspend fun deleteNote(id: String) = firestoreDataSource.deleteNote(id)

    override suspend fun deleteNotes(notes: List<Note>) = firestoreDataSource.deleteNotes(notes)

    override suspend fun updateNote(id: String, newTitle: String, newBody: String) =
        firestoreDataSource.updateNote(id, newTitle, newBody)
}