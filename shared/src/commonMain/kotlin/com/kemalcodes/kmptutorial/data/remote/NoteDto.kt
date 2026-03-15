package com.kemalcodes.kmptutorial.data.remote

import com.kemalcodes.kmptutorial.model.Note
import com.kemalcodes.kmptutorial.model.NoteColor
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoteDto(
    val id: Long,
    val title: String,
    val content: String,
    @SerialName("created_at") val createdAt: Long,
    @SerialName("updated_at") val updatedAt: Long,
    @SerialName("is_favorite") val isFavorite: Boolean = false,
    val color: String = "DEFAULT"
)

fun NoteDto.toNote(): Note = Note(
    id = id,
    title = title,
    content = content,
    createdAt = Instant.fromEpochMilliseconds(createdAt),
    updatedAt = Instant.fromEpochMilliseconds(updatedAt),
    isFavorite = isFavorite,
    color = try {
        NoteColor.valueOf(color)
    } catch (_: IllegalArgumentException) {
        NoteColor.DEFAULT
    }
)

fun Note.toDto(): NoteDto = NoteDto(
    id = id,
    title = title,
    content = content,
    createdAt = createdAt.toEpochMilliseconds(),
    updatedAt = updatedAt.toEpochMilliseconds(),
    isFavorite = isFavorite,
    color = color.name
)
