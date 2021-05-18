package ru.chalexdev.todoapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.chalexdev.todoapp.business.data.cache.implementation.NoteCacheDataSourceImpl
import ru.chalexdev.todoapp.business.data.network.implementation.NoteNetworkDataSourceImpl
import ru.chalexdev.todoapp.business.domain.model.Note
import ru.chalexdev.todoapp.business.domain.model.NoteFactory
import ru.chalexdev.todoapp.business.interactors.notelist.InsertNewNote
import ru.chalexdev.todoapp.business.interactors.notelist.NoteList
import ru.chalexdev.todoapp.business.interactors.notelist.NoteListInteractors
import ru.chalexdev.todoapp.databinding.ActivityMainBinding
import ru.chalexdev.todoapp.framework.datasource.cache.abstraction.NoteDaoService
import ru.chalexdev.todoapp.framework.datasource.network.abstraction.NoteFirestoreDataSource
import ru.chalexdev.todoapp.framework.presentation.notelist.NoteListViewModel
import ru.chalexdev.todoapp.framework.presentation.notelist.state.NoteListIntent

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val scope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val noteCacheDataSourceImpl = NoteCacheDataSourceImpl(object : NoteDaoService {
            override suspend fun getAllNotes(): List<Note> {
                return emptyList()
            }

            override suspend fun insertNote(note: Note): Long {
                TODO("Not yet implemented")
            }

            override suspend fun insertNotes(notes: List<Note>): LongArray {
                TODO("Not yet implemented")
            }

            override suspend fun deleteNote(id: String): Int {
                TODO("Not yet implemented")
            }

            override suspend fun deleteNotes(notes: List<Note>): Int {
                TODO("Not yet implemented")
            }

            override suspend fun updateNote(id: String, newTitle: String, newBody: String): Int {
                TODO("Not yet implemented")
            }

            override suspend fun searchNoteById(id: String): Note? {
                TODO("Not yet implemented")
            }

        })

        val noteNetworkDataSourceImpl = NoteNetworkDataSourceImpl(object : NoteFirestoreDataSource {
            override suspend fun getAllNotes(): List<Note> {
                return emptyList()
            }

            override suspend fun insertNote(note: Note): Long {
                TODO("Not yet implemented")
            }

            override suspend fun insertNotes(notes: List<Note>): LongArray {
                TODO("Not yet implemented")
            }

            override suspend fun deleteNote(id: String): Int {
                TODO("Not yet implemented")
            }

            override suspend fun deleteNotes(notes: List<Note>): Int {
                TODO("Not yet implemented")
            }

            override suspend fun updateNote(id: String, newTitle: String, newBody: String): Int {
                TODO("Not yet implemented")
            }
        })

        val noteListInteractors = NoteListInteractors(
                NoteList(noteCacheDataSourceImpl),
                InsertNewNote(noteCacheDataSourceImpl, noteNetworkDataSourceImpl)
        )
        val viewModel = NoteListViewModel(noteListInteractors, NoteFactory())



        binding.tvHello.setOnClickListener {
            viewModel.postIntent(NoteListIntent.FetchNoteList)
        }
        viewModel.noteListState.onEach {
            Log.d("AABB", "noteListState $it")
        }.launchIn(scope)
    }
}