package com.kemalcodes.kmptutorial.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.kemalcodes.kmptutorial.db.NotesDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver =
        NativeSqliteDriver(NotesDatabase.Schema, "notes.db")
}
