// NoteRepository.kt
// Repository layer that wraps SQLDelight queries with Kotlin Flow.
//
// This class provides a clean API for the rest of the app to interact
// with the notes database. It uses Flow so the UI can automatically
// update whenever the data changes (reactive pattern).
//
// Key concepts:
// - asFlow() converts a SQLDelight query into a Kotlin Flow
// - mapToList() transforms the Flow to emit a List of results
// - mapToOneOrNull() transforms the Flow to emit a single result or null

package com.kemalcodes.kmptutorial.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.kemalcodes.kmptutorial.db.AppDatabase
import com.kemalcodes.kmptutorial.db.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock

// NoteRepository takes an AppDatabase instance.
// The database is created using DriverFactory (see DriverFactory.kt).
class NoteRepository(database: AppDatabase) {

    // Get a reference to the generated query class.
    // SQLDelight creates "noteQueries" from our Note.sq file.
    private val queries = database.noteQueries

    // Get all notes as a reactive Flow.
    // Whenever a note is inserted, updated, or deleted, this Flow
    // will automatically emit the updated list.
    fun getAllNotes(): Flow<List<Notes>> {
        return queries.selectAll().asFlow().mapToList(Dispatchers.IO)
    }

    // Get a single note by ID as a reactive Flow.
    // Emits null if no note is found with the given ID.
    fun getNoteById(id: Long): Flow<Notes?> {
        return queries.selectById(id).asFlow().mapToOneOrNull(Dispatchers.IO)
    }

    // Search notes by title as a reactive Flow.
    // The SQL uses LIKE with wildcards for partial matching.
    fun searchByTitle(query: String): Flow<List<Notes>> {
        return queries.searchByTitle(query).asFlow().mapToList(Dispatchers.IO)
    }

    // Insert a new note into the database.
    // We use the current time in milliseconds for created_at and updated_at.
    fun insertNote(title: String, content: String, isCompleted: Boolean = false) {
        val now = currentTimeMillis()
        queries.insert(
            title = title,
            content = content,
            is_completed = if (isCompleted) 1L else 0L,
            created_at = now,
            updated_at = now
        )
    }

    // Update an existing note's title and content.
    // The updated_at timestamp is refreshed automatically.
    fun updateNote(id: Long, title: String, content: String) {
        queries.updateNote(
            title = title,
            content = content,
            updated_at = currentTimeMillis(),
            id = id
        )
    }

    // Toggle a note's completed status (done/not done).
    fun toggleCompleted(id: Long) {
        queries.toggleCompleted(
            updated_at = currentTimeMillis(),
            id = id
        )
    }

    // Delete a note by its ID.
    fun deleteNote(id: Long) {
        queries.deleteById(id)
    }

    // Count the total number of notes.
    // Returns the count directly (not as a Flow).
    fun countAll(): Long {
        return queries.countAll().executeAsOne()
    }

    // Helper to get current time in milliseconds.
    // We use this for created_at and updated_at timestamps.
    // Clock.System.now() from kotlinx-datetime works on all platforms.
    private fun currentTimeMillis(): Long {
        return Clock.System.now().toEpochMilliseconds()
    }
}
