package ru.chalexdev.todoapp.framework.presentation.notelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.chalexdev.todoapp.business.data.cache.CacheResult.Error
import ru.chalexdev.todoapp.business.data.cache.CacheResult.Success
import ru.chalexdev.todoapp.business.domain.model.Note
import ru.chalexdev.todoapp.business.domain.model.NoteFactory
import ru.chalexdev.todoapp.business.interactors.notelist.NoteListInteractors
import ru.chalexdev.todoapp.framework.presentation.notelist.state.NoteListIntent
import ru.chalexdev.todoapp.framework.presentation.notelist.state.NoteListState

class NoteListViewModel(
        private val noteListInteractors: NoteListInteractors,
        private val noteFactory: NoteFactory
) : ViewModel() {

    private val _noteListIntent = MutableSharedFlow<NoteListIntent>()
    private val _noteListState = MutableStateFlow<NoteListState>(NoteListState.Loading)
    val noteListState = _noteListState.asStateFlow()

    init {
        viewModelScope.launch {
            _noteListIntent.map {
                when (it) {
                    is NoteListIntent.FetchNoteList -> fetchNoteList()
                    is NoteListIntent.AddNote -> insertNote(it)
                }
            }
        }
    }

    fun postIntent(intent: NoteListIntent) {
        _noteListIntent.tryEmit(intent)
    }

    private fun fetchNoteList() {
        viewModelScope.launch {
            _noteListState.value = NoteListState.Loading

            _noteListState.value = when (val result = noteListInteractors.noteList.getNotes()) {
                is Success<List<Note>?> -> {
                    NoteListState.NoteList(result.value ?: ArrayList())
                }
                is Error -> {
                    NoteListState.Error(result.errorMessage)
                }
            }
        }
    }

    private fun insertNote(noteListIntent: NoteListIntent.AddNote) {
        val newNote = noteFactory.createNote(
                id = noteListIntent.id,
                title = noteListIntent.title,
                body = noteListIntent.body
        )
        viewModelScope.launch {
            _noteListState.value = NoteListState.Loading
            noteListInteractors.insertNewNote.insertNewNote(newNote)
                    .collect {
                        when (it) {
                            is Success<Long?> -> {
                                val insertResult = it.value ?: -1L
                                if (insertResult > 0L) {
                                    _noteListState.value = NoteListState.NewNote(newNote)
                                } else {
                                    NoteListState.Error(INSERT_NOTE_FAILED)
                                }
                            }
                            is Error -> {
                                NoteListState.Error(it.errorMessage)
                            }
                        }
                    }

        }
    }

    companion object {
        private val INSERT_NOTE_FAILED = "Failed to insert new note."
    }
}