package com.kemalcodes.kmptutorial.data.local

import com.kemalcodes.kmptutorial.db.NoteEntity
import com.kemalcodes.kmptutorial.model.Note
import com.kemalcodes.kmptutorial.model.NoteColor
import kotlin.time.Instant

fun NoteEntity.toNote(): Note = Note(
    id = id,
    title = title,
    content = content,
    createdAt = Instant.fromEpochMilliseconds(created_at),
    updatedAt = Instant.fromEpochMilliseconds(updated_at),
    isFavorite = is_favorite == 1L,
    color = try {
        NoteColor.valueOf(color)
    } catch (_: IllegalArgumentException) {
        NoteColor.DEFAULT
    }
)
