package com.kemalcodes.kmptutorial.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class NoteApiClient(private val httpClient: HttpClient) {

    private val baseUrl = "https://api.example.com/notes"

    suspend fun fetchAllNotes(): List<NoteDto> =
        httpClient.get(baseUrl).body()

    suspend fun fetchNoteById(id: Long): NoteDto =
        httpClient.get("$baseUrl/$id").body()

    suspend fun createNote(dto: NoteDto): NoteDto =
        httpClient.post(baseUrl) {
            contentType(ContentType.Application.Json)
            setBody(dto)
        }.body()

    suspend fun updateNote(dto: NoteDto): NoteDto =
        httpClient.put("$baseUrl/${dto.id}") {
            contentType(ContentType.Application.Json)
            setBody(dto)
        }.body()

    suspend fun deleteNote(id: Long) {
        httpClient.delete("$baseUrl/$id")
    }
}
