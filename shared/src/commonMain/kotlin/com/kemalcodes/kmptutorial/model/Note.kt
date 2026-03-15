package com.kemalcodes.kmptutorial.model

import kotlin.time.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id: Long = 0,
    val title: String,
    val content: String,
    val createdAt: Instant,
    val updatedAt: Instant,
    val isFavorite: Boolean = false,
    val color: NoteColor = NoteColor.DEFAULT
)

@Serializable
enum class NoteColor {
    DEFAULT,
    RED,
    ORANGE,
    YELLOW,
    GREEN,
    BLUE,
    PURPLE
}
