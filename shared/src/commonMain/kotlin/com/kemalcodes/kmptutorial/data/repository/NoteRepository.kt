package com.kemalcodes.kmptutorial.data.repository

import com.kemalcodes.kmptutorial.data.local.LocalNoteDataSource
import com.kemalcodes.kmptutorial.model.Note
import com.kemalcodes.kmptutorial.model.NoteColor
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val localDataSource: LocalNoteDataSource) {

    fun getAllNotes(): Flow<List<Note>> =
        localDataSource.getAllNotes()

    fun getNoteById(id: Long): Flow<Note?> =
        localDataSource.getNoteById(id)

    fun searchNotes(query: String): Flow<List<Note>> =
        localDataSource.searchNotes(query)

    fun getFavoriteNotes(): Flow<List<Note>> =
        localDataSource.getFavoriteNotes()

    fun createNote(title: String, content: String, color: NoteColor = NoteColor.DEFAULT): Long =
        localDataSource.insertNote(title, content, color)

    fun updateNote(note: Note) =
        localDataSource.updateNote(note)

    fun deleteNote(id: Long) =
        localDataSource.deleteNote(id)
}
