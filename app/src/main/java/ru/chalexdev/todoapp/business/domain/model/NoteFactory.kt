package ru.chalexdev.todoapp.business.domain.model

import ru.chalexdev.todoapp.business.domain.util.DateUtil
import java.util.*

class NoteFactory(private val dateUtil: DateUtil) {

    fun createNote(
        id: String? = null,
        title: String,
        body: String? = null
    ): Note {
        return Note(
            id = id ?: UUID.randomUUID().toString(),
            title = title,
            body = body ?: "",
            createdAt = dateUtil.getCurrentTimestamp(),
            updatedAt = dateUtil.getCurrentTimestamp(),
        )
    }

    fun createNoteList(numNotes: Int): List<Note> {
        val notes = mutableListOf<Note>()
        for (i in 0 until numNotes) {
            notes.add(
                createNote(
                    null,
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString()
                )
            )
        }
        return notes
    }
}