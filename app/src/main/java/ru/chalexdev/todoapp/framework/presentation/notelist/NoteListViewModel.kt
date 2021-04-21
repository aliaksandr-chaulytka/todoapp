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
import ru.chalexdev.todoapp.business.interactors.notelist.NoteListInteractors
import ru.chalexdev.todoapp.framework.presentation.notelist.state.NoteListIntent
import ru.chalexdev.todoapp.framework.presentation.notelist.state.NoteListState

class NoteListViewModel(
    private val noteListInteractors: NoteListInteractors
) : ViewModel() {
    val noteListIntent = Channel<NoteListIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<NoteListState>(NoteListState.Loading)
    val state: StateFlow<NoteListState> get() = _state

    init {
        viewModelScope.launch {
            noteListIntent.consumeAsFlow().collect {
                when (it) {
                    is NoteListIntent.FetchNoteList -> fetchNoteList()
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
}