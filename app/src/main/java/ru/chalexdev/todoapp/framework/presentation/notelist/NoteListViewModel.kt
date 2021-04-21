package ru.chalexdev.todoapp.framework.presentation.notelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
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
    val noteListIntent = Channel<NoteListIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<NoteListState>(NoteListState.Loading)
    val state: StateFlow<NoteListState> get() = _state

    init {
        viewModelScope.launch {
            noteListIntent.consumeAsFlow().collect {
                when (it) {
                    is NoteListIntent.FetchNoteList -> fetchNoteList()
                    is NoteListIntent.AddNote -> insertNote(it)
                }
            }
        }
    }

    private fun fetchNoteList() {
        viewModelScope.launch {
            _state.value = NoteListState.Loading

            _state.value = when (val result = noteListInteractors.noteList.getNotes()) {
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
            _state.value = NoteListState.Loading
            noteListInteractors.insertNewNote.insertNewNote(newNote)
                .collect {
                    when (it) {
                        is Success<Long?> -> {
                            val insertResult = it.value ?: -1L
                            if (insertResult > 0L) {
                                _state.value = NoteListState.NewNote(newNote)
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