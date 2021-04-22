package ru.chalexdev.todoapp.business.interactors.notelist

import kotlinx.coroutines.Dispatchers.IO
import ru.chalexdev.todoapp.business.data.cache.abstraction.NoteCacheDataSource
import ru.chalexdev.todoapp.business.data.util.safeCacheCall

class NoteList(private val noteCacheDataSource: NoteCacheDataSource) {

    suspend fun getNotes() = safeCacheCall(IO) {
        noteCacheDataSource.getAllNotes()
    }
}