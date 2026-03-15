package com.kemalcodes.kmptutorial.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.kemalcodes.kmptutorial.db.NoteEntity
import com.kemalcodes.kmptutorial.db.NotesDatabase
import com.kemalcodes.kmptutorial.model.Note
import com.kemalcodes.kmptutorial.model.NoteColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.time.Clock

class LocalNoteDataSource(private val db: NotesDatabase) {

    private val queries = db.noteQueries

    fun getAllNotes(): Flow<List<Note>> =
        queries.getAllNotes()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities: List<NoteEntity> -> entities.map { it.toNote() } }

    fun getNoteById(id: Long): Flow<Note?> =
        queries.getNoteById(id)
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
            .map { entity: NoteEntity? -> entity?.toNote() }

    fun searchNotes(query: String): Flow<List<Note>> =
        queries.searchNotes(query, query)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities: List<NoteEntity> -> entities.map { it.toNote() } }

    fun getFavoriteNotes(): Flow<List<Note>> =
        queries.getFavoriteNotes()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities: List<NoteEntity> -> entities.map { it.toNote() } }

    fun insertNote(title: String, content: String, color: NoteColor = NoteColor.DEFAULT): Long {
        val now = Clock.System.now().toEpochMilliseconds()
        queries.insertNote(
            title = title,
            content = content,
            created_at = now,
            updated_at = now,
            is_favorite = 0L,
            color = color.name
        )
        return queries.getLastInsertId().executeAsOne()
    }

    fun updateNote(note: Note) {
        val now = Clock.System.now().toEpochMilliseconds()
        queries.updateNote(
            title = note.title,
            content = note.content,
            updated_at = now,
            is_favorite = if (note.isFavorite) 1L else 0L,
            color = note.color.name,
            id = note.id
        )
    }

    fun deleteNote(id: Long) {
        queries.deleteNote(id)
    }
}
