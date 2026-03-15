package com.kemalcodes.kmptutorial.data.repository

import com.kemalcodes.kmptutorial.data.local.LocalNoteDataSource
import com.kemalcodes.kmptutorial.data.remote.NoteApiClient
import com.kemalcodes.kmptutorial.data.remote.toDto
import com.kemalcodes.kmptutorial.data.remote.toNote
import com.kemalcodes.kmptutorial.model.Note
import com.kemalcodes.kmptutorial.model.NoteColor
import kotlinx.coroutines.flow.Flow

class NoteRepository(
    private val localDataSource: LocalNoteDataSource,
    private val apiClient: NoteApiClient
) {

    fun getAllNotes(): Flow<List<Note>> =
        localDataSource.getAllNotes()

    fun getNoteById(id: Long): Flow<Note?> =
        localDataSource.getNoteById(id)

    fun searchNotes(query: String): Flow<List<Note>> =
        localDataSource.searchNotes(query)

    fun getFavoriteNotes(): Flow<List<Note>> =
        localDataSource.getFavoriteNotes()

    fun createNote(title: String, content: String, color: NoteColor = NoteColor.DEFAULT): Long =
        localDataSource.insertNote(title, content, color, synced = false)

    fun updateNote(note: Note) =
        localDataSource.updateNote(note, synced = false)

    fun deleteNote(id: Long) =
        localDataSource.deleteNote(id)

    /**
     * Sync local data with the remote API.
     *
     * 1. Push unsynced local notes to the server.
     * 2. Fetch all notes from the server and merge into local DB.
     *
     * If the API is unavailable, exceptions are caught and local data
     * remains untouched -- the offline-first guarantee.
     */
    suspend fun sync(): SyncResult {
        return try {
            // Step 1: Push unsynced notes to the server
            val unsyncedNotes = localDataSource.getUnsyncedNotes()
            for (note in unsyncedNotes) {
                val dto = note.toDto()
                if (note.id > 0) {
                    apiClient.createNote(dto)
                }
                localDataSource.markSynced(note.id)
            }

            // Step 2: Fetch remote notes and merge into local DB
            val remoteNotes = apiClient.fetchAllNotes().map { it.toNote() }
            for (remoteNote in remoteNotes) {
                localDataSource.insertNoteFromRemote(remoteNote)
            }

            SyncResult.Success(pushed = unsyncedNotes.size, pulled = remoteNotes.size)
        } catch (e: Exception) {
            SyncResult.Error(e.message ?: "Sync failed")
        }
    }
}

sealed class SyncResult {
    data class Success(val pushed: Int, val pulled: Int) : SyncResult()
    data class Error(val message: String) : SyncResult()
}
