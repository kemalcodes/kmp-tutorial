package com.kemalcodes.kmptutorial.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kemalcodes.kmptutorial.data.repository.NoteRepository
import com.kemalcodes.kmptutorial.data.repository.SyncResult
import com.kemalcodes.kmptutorial.model.Note
import com.kemalcodes.kmptutorial.model.NoteColor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: NoteRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _syncError = MutableStateFlow<String?>(null)
    val syncError: StateFlow<String?> = _syncError

    @OptIn(ExperimentalCoroutinesApi::class)
    val notes: StateFlow<List<Note>> = _searchQuery
        .flatMapLatest { query ->
            if (query.isBlank()) {
                repository.getAllNotes()
            } else {
                repository.searchNotes(query)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        sync()
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun sync() {
        viewModelScope.launch {
            _isLoading.value = true
            _syncError.value = null
            when (val result = repository.sync()) {
                is SyncResult.Success -> { /* sync complete */ }
                is SyncResult.Error -> _syncError.value = result.message
            }
            _isLoading.value = false
        }
    }

    fun clearSyncError() {
        _syncError.value = null
    }

    fun createNote(title: String, content: String, color: NoteColor = NoteColor.DEFAULT) {
        viewModelScope.launch {
            repository.createNote(title, content, color)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            repository.updateNote(note)
        }
    }

    fun toggleFavorite(note: Note) {
        viewModelScope.launch {
            repository.updateNote(note.copy(isFavorite = !note.isFavorite))
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch {
            repository.deleteNote(id)
        }
    }
}
