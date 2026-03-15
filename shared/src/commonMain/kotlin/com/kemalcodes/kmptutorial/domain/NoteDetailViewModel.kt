package com.kemalcodes.kmptutorial.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kemalcodes.kmptutorial.data.repository.NoteRepository
import com.kemalcodes.kmptutorial.model.Note
import com.kemalcodes.kmptutorial.model.NoteColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class NoteDetailViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    private val _note = MutableStateFlow<Note?>(null)
    val note: StateFlow<Note?> = _note

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    private val _content = MutableStateFlow("")
    val content: StateFlow<String> = _content

    private val _color = MutableStateFlow(NoteColor.DEFAULT)
    val color: StateFlow<NoteColor> = _color

    fun loadNote(id: Long) {
        viewModelScope.launch {
            repository.getNoteById(id)
                .filterNotNull()
                .collect { note ->
                    _note.value = note
                    _title.value = note.title
                    _content.value = note.content
                    _color.value = note.color
                }
        }
    }

    fun onTitleChange(title: String) {
        _title.value = title
    }

    fun onContentChange(content: String) {
        _content.value = content
    }

    fun onColorChange(color: NoteColor) {
        _color.value = color
    }

    fun saveNote(): Long {
        val existingNote = _note.value
        return if (existingNote != null) {
            repository.updateNote(
                existingNote.copy(
                    title = _title.value,
                    content = _content.value,
                    color = _color.value
                )
            )
            existingNote.id
        } else {
            repository.createNote(
                title = _title.value,
                content = _content.value,
                color = _color.value
            )
        }
    }
}
