package ru.chalexdev.todoapp.business.interactors.notelist

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flow
import ru.chalexdev.todoapp.business.data.cache.CacheResult
import ru.chalexdev.todoapp.business.data.cache.abstraction.NoteCacheDataSource
import ru.chalexdev.todoapp.business.data.network.abstraction.NoteNetworkDataSource
import ru.chalexdev.todoapp.business.data.util.safeApiCall
import ru.chalexdev.todoapp.business.data.util.safeCacheCall
import ru.chalexdev.todoapp.business.domain.model.Note

class InsertNewNote(
    private val noteCacheDataSource: NoteCacheDataSource,
    private val noteNetworkDataSource: NoteNetworkDataSource
) {

    fun insertNewNote(newNote: Note) = flow {
        val cacheResult = safeCacheCall(IO) {
            noteCacheDataSource.insertNote(newNote)
        }

        emit(cacheResult)

        if (cacheResult is CacheResult.Success) {
            updateNetwork(newNote)
        }
    }

    private suspend fun updateNetwork(newNote: Note) {
        safeApiCall(IO) {
            noteNetworkDataSource.insertNote(newNote)
        }
    }
}